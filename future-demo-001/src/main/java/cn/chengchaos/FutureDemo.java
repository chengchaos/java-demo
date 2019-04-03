package cn.chengchaos;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.pattern.Patterns;
import akka.util.Timeout;
import cn.chengchaos.actor.FirstActor;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

public class FutureDemo {


    public static final ActorSystem ACTOR_SYSTEM = ActorSystem.create("actor-system");

    public static volatile boolean USE_ASK = false;

    public static ConcurrentMap<String, CompletableFuture<Integer>> map =
            new ConcurrentHashMap<>();


    public static void waitSomeTime(int seconds) {

        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void run() {

        ActorRef firstActor = ACTOR_SYSTEM.actorOf(Props.create(FirstActor.class));


        Future<Object> ask = Patterns
                .ask(firstActor
                        , "1000"
                        , new Timeout(Duration.create(7000, "millisecond")));

        CompletableFuture<Object> future = scala.compat.java8.FutureConverters.toJava(ask)
                .toCompletableFuture()
                .exceptionally(thr -> {
                    System.err.println("in exceptionally: "+  thr);
                    return -1;
                });

        future.whenComplete((res, thr) -> {

            if (Objects.nonNull(res)) {
                System.err.println(">>> === >>> "+ res);
            }
        });

        waitSomeTime(10);

        ACTOR_SYSTEM.terminate();
        System.out.println("actor system shutdown now 。");
    }

    public static void main(String[] args) {
//        ActorSystem system = ActorSystem.create("akkademy");

        new FutureDemo().run();

    }
}
