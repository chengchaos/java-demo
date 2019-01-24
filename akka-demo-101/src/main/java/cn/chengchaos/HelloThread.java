package cn.chengchaos;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import cn.chengchaos.actor.PrintActor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * 功能的详细描述
 * </p>
 *
 * @author chengchaos[as]Administrator - 2019/1/22 0022 上午 10:27 <br />
 * @see 【相关类方法】
 * @since 1.1.0
 */
public class HelloThread {

    private static final Logger LOGGER = LoggerFactory.getLogger(HelloThread.class);

    public static final ActorSystem threadActorSystem = ActorSystem.create("thread-actor-system");

    public static void main(String[] args) throws InterruptedException {
        LOGGER.info("hello");
        Props props = Props.create(PrintActor.class).withDispatcher("chaos-dispatcher");
        ActorRef printActor = threadActorSystem.actorOf(props, "print");

        for (int i = 0; i < 2; i++) {
            printActor.tell("hello", ActorRef.noSender());
            printActor.tell("world", ActorRef.noSender());
            printActor.tell("input your email.", ActorRef.noSender());
            printActor.tell("bye", ActorRef.noSender());
        }

        TimeUnit.SECONDS.sleep(10L);
        threadActorSystem.terminate();

    }
}
