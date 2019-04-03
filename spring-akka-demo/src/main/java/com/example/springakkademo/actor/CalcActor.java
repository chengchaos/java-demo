package com.example.springakkademo.actor;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.pf.ReceiveBuilder;
import scala.PartialFunction;
import scala.runtime.BoxedUnit;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class CalcActor extends AbstractActor {

    private final LoggingAdapter log = Logging.getLogger(
            context().system(), this
    );

    final Random random = new Random();

    @Override
    public PartialFunction<Object, BoxedUnit> receive() {

        final ActorRef isMe = self();
        return
                ReceiveBuilder.match(Integer.class, (in) -> {

                    int next = random.nextInt(5);
                    log.info("等一会 ==> {}", next);
                    TimeUnit.SECONDS.sleep(next);
                    Integer result = in + next;
                    sender().tell(result, isMe);
                })
                .match(CalcRequest.class, (cr) -> {
                    log.info("CalcRequest -==> {}", cr);
                    int next = random.nextInt(5);
                    log.info("等一会 ==> {}", next);
                    TimeUnit.SECONDS.sleep(next);
                    Integer result = cr.getInput() + next;
                    CalcResult calcResult = new CalcResult(cr.getId(), result);
                    ActorRef sender = sender();
                    log.info("sender : {}", sender);
                    sender.tell(calcResult, isMe);
                })
                .matchAny(x -> log.warning("unknown message: {}", x))
                .build();
    }
}
