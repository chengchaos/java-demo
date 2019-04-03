package cn.chengchaos.actor;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import cn.chengchaos.HelloThread;

import java.util.concurrent.CompletableFuture;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * 功能的详细描述
 * </p>
 *
 * @author chengchaos[as]Administrator - 2019/1/22 0022 上午 10:30 <br />
 * @see 【相关类方法】
 * @since 1.1.0
 */
public class PrintActor extends UntypedActor {

    private final LoggingAdapter logging = Logging.getLogger(context().system(), this);

    @Override
    public void onReceive(Object message) throws Throwable {

        Props props = Props.create(PrintActor2.class)
                .withDispatcher("chaos-dispatcher");

        ActorRef printActor2Ref = context().actorOf(props);
        logging.info("{} ==> to printActor2 ==> {}", Thread.currentThread().getName(), message);
        printActor2Ref.tell(message, self());


        CompletableFuture.runAsync(() -> {
            logging.info("{} ==> runAsync 1 ... {}",
                    Thread.currentThread().getName(),
                    message);
        }, HelloThread.threadActorSystem.dispatcher());


        CompletableFuture.runAsync(() -> {
            logging.info("{} ==> runAsync 2 ... {}",
                    Thread.currentThread().getName(),
                    message);

        }, HelloThread.threadActorSystem.dispatchers().lookup("my-pinned-dispatcher"));
    }
}
