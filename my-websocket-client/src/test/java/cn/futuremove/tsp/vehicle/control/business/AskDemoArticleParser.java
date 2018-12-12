package cn.futuremove.tsp.vehicle.control.business;

import akka.actor.*;
import akka.japi.pf.ReceiveBuilder;
import akka.util.Timeout;
import scala.PartialFunction;
import scala.concurrent.duration.Duration;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class AskDemoArticleParser extends AbstractActor {


    private final ActorSelection cacheActor;
    private final ActorSelection httpClientActor;
    private final ActorSelection articleParseActor;
    private final Timeout timeout;

    public AskDemoArticleParser(
            String cacheActorPath,
            String httpClientActorPath,
            String articleParseActorPath,
            Timeout timeout) {

        this.cacheActor = context().actorSelection(cacheActorPath);
        this.httpClientActor = context().actorSelection(httpClientActorPath);
        this.articleParseActor = context().actorSelection(articleParseActorPath);
        this.timeout = timeout;

    }

    /**
     *
     * @return
     */
    public PartialFunction receive() {

        return ReceiveBuilder
                .match(ParseArticle.class,  msg -> {
                    // 建立一个特殊的 extraActor .. sender() 作为他的报告者.
                    ActorRef extraActor = buildExtraActor(sender(), msg.url);
                    // 请求缓存, extraActor 作为 sender ..
                    cacheActor.tell(new GetRequest(msg.url), extraActor);
                    // 请求 http, extraActor 作为 sender ..
                    httpClientActor.tell(msg.url, extraActor);
                    // 执行调度, 在达到超时时间后想 extraActor 发送一条 'timeout' 的消息
                    context().system().scheduler().scheduleOnce(
                            timeout.duration() // 超时时间
                            , extraActor  // 接受者
                            , "timeout" // 消息内容
                            , context().system().dispatcher() // ExecutionContext 隐式
                            , ActorRef.noSender() // 发送者
                    );

                })
                .match(String.class, msg -> {
                    String name = "hello " + msg;
                    System.out.println("kao "+ name);
                })
                .build();

    }

    public void v1(ActorRef actor) {
        context().system()
                .scheduler()
                .scheduleOnce(Duration.create(3, TimeUnit.SECONDS)
                        , actor
                        , "timeout"
                        , context().system().dispatcher()
                        , ActorRef.noSender());

    }

    /**
     * ExtraActor 是一个匿名 Actor
     * 定义了对上面 3 个消息的响应...
     *
     *
     * @param senderRef
     * @param uri
     * @return
     */
    private ActorRef buildExtraActor(final ActorRef senderRef, final String uri) {

        class MyActor extends AbstractActor {

            public MyActor() {

                receive(ReceiveBuilder
                        .matchEquals(String.class, "timeout"::equals, x -> {
                            senderRef.tell(new Status.Failure(new TimeoutException("timeout")), self());
                            context().stop(self());
                        })
                        .match(HttpResponse.class, httpResponse -> {
                            // if we get the cache response first,
                            // then we handle it and shutdown.
                            // the cache response will come back before
                            // the http response so we never parse in this case.
                            articleParseActor.tell(new ParseHtmlArticle(uri, httpResponse.body), self());
                        })
                        // if we get the cache resposne first,
                        // then we handle it and shut down.
                        .match(String.class, body -> {
                            // the cache response will come back before
                            // the HTTP response so we never parse in this case.
                            senderRef.tell(body, self());
                            context().stop(self());
                        })
                        // if we get the parsed article back,
                        // then we've just parsed it
                        .match(ArticleBody.class, articleBody -> {
                            cacheActor.tell(new SetRequest(articleBody.uri, articleBody.body), self());
                            senderRef.tell(articleBody.body, self());
                            context().stop(self());
                        })
                        .matchAny(t -> {
                            // we can get a cache miss
                            System.out.println("ignore msg: "+ t.getClass());
                        })
                        .build());
            }
        }

        return context().actorOf(Props.create(MyActor.class, () -> new MyActor()));

    }


    public static class ParseArticle {

        public String url;

    }

    public static class HttpResponse {
        public String body;
    }
}