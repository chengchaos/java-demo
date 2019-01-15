package cn.chengchaos.actor;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.Status;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.pf.FI;
import akka.japi.pf.ReceiveBuilder;
import akka.pattern.Patterns;
import akka.util.Timeout;
import cn.chengchaos.FutureDemo;
import scala.PartialFunction;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;
import scala.runtime.BoxedUnit;

import java.util.Objects;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * 功能的详细描述
 * </p>
 *
 * @author chengchao - 19-1-11 下午3:46 <br />
 * @see [相关类方法]
 * @since 1.0.0
 */
public class FirstActor extends AbstractActor {

    private final LoggingAdapter logger = Logging.getLogger(getContext().system(), this);


    @Override
    public PartialFunction<Object, BoxedUnit> receive() {

        logger.info("sender is : {}", sender());

        return ReceiveBuilder.create()
                .match(String.class, this.apply)
                .matchAny(other -> sender().tell(new Status.Failure(new RuntimeException("kao, 不懂")),
                        self()))
                .build();
    }

    private final FI.UnitApply<String> apply = (input) -> {

        logger.info("input ==> {}", input);

        ActorRef secondActorRef = FutureDemo.ACTOR_SYSTEM.actorOf(
                Props.create(SecondActor.class));

        Integer data = Integer.valueOf(input, 10);

        final ActorRef sender = sender();

        if (FutureDemo.USE_ASK) {


            Future<Object> ask = Patterns.ask(secondActorRef, data, new Timeout(
                    Duration.create(2000L, "millisecond")));

            CompletableFuture<Object> future =
                    scala.compat.java8.FutureConverters.toJava(ask)
                            .toCompletableFuture();

            future.whenComplete((res, thr) -> {

                if (Objects.nonNull(thr)) {
                    logger.error(thr, "Frist when complete 监测到异常");
                }
                if (Objects.nonNull(res)) {
                    logger.info("receive ... {}", res);
                    System.err.println(sender());
                    sender.tell(res, self());
                }
            });
        } else {
            CompletableFuture<Integer> future = new CompletableFuture<>();
            FutureDemo.map.put("chaos", future);
            secondActorRef.tell(data, sender);

            try {
                Integer integer = future.get(2L, TimeUnit.SECONDS);
                sender.tell(integer, self());
            } catch (CancellationException ce) {
                logger.error("ex ==> {}", ce.getClass().getName());
                sender.tell(-888, self());
            } catch (Exception e) {
                sender.tell(-999, self());
            }


        }


    };
}
