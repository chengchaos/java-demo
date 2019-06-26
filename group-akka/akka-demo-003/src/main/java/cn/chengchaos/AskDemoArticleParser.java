package cn.chengchaos;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.pf.ReceiveBuilder;
import akka.util.Timeout;
import scala.PartialFunction;
import scala.runtime.BoxedUnit;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import static akka.pattern.Patterns.ask;
import static cn.chengchaos.FutureHelper.toJava;

public class AskDemoArticleParser extends AbstractActor {

    private final LoggingAdapter logger = Logging.getLogger(context().system(), this);


    private final ActorSelection cacheActor;

    private final ActorSelection httpClientActor;

    private final ActorSelection articleParseActor;


    private final Timeout timeout;

    public AskDemoArticleParser(
            String cachePctorPath,
            String httpClientActorPath,
            String articleParseActorPath,
            Timeout timeout
    ) {

        this.cacheActor = context().actorSelection(cachePctorPath);
        this.httpClientActor = context().actorSelection(httpClientActorPath);
        this.articleParseActor = context().actorSelection(articleParseActorPath);
        this.timeout = timeout;
    }

    @Override
    public PartialFunction<Object, BoxedUnit> receive() {
        return ReceiveBuilder
                .match(ParseArticle.class, msg -> {
                    final CompletionStage<?> cacheResult = toJava(
                            ask(cacheActor, new GetRequest(msg.url), timeout)
                    );


                    final CompletionStage<?> result = cacheResult.handle(
                            (x, t) -> (Objects.nonNull(x))
                                    ? CompletableFuture.completedFuture(x)
                                    : toJava(ask(httpClientActor, msg.url, timeout))
                                    .thenCompose(
                                            rawArticle -> toJava(
                                                    ask(articleParseActor, new ParseHtmlArticle(msg.url, ((HttpResponse) rawArticle).body), timeout)))


                    ).thenCompose(x -> x);

                    final ActorRef senderRef = sender();
                    result.handle((x, t) -> {
                        if (Objects.nonNull(x)) {
                            if (x instanceof ArticleBody) {
                                String body = ((ArticleBody) x).body;
                                cacheActor.tell(body, self());
                                senderRef.tell(body, self());
                            } else if (x instanceof String) {
                                senderRef.tell(x, self());
                            }
                        } else {
                            senderRef.tell(new akka.actor.Status.Failure((Throwable) t), self());
                        }
                        return null;
                    });
                })
                .build();
    }
}
