package com.example.springakkademo.actor;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.pf.ReceiveBuilder;
import scala.PartialFunction;
import scala.runtime.BoxedUnit;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class MasterActor extends AbstractActor {

    private final LoggingAdapter logger = Logging.getLogger(context().system(), this);


    private ConcurrentMap<Long, CompletableFuture<Integer>> map =
            new ConcurrentHashMap<>();

    private ActorRef calcActor = ActorManager.actorSystem.actorOf(
            Props.create(CalcActor.class)
            , "calcActor"
    );

    private ActorRef createCalcActor() {
        return ActorManager.actorSystem.actorOf(
                Props.create(CalcActor.class));
    }

    @Override
    public PartialFunction<Object, BoxedUnit> receive() {

        return ReceiveBuilder
                .match(CalcWrapper.class, cw -> {
                    map.put(cw.getId(), cw.getFuture());
                    createCalcActor().tell(cw, self());
                })
                .match(CalcResult.class, cw -> {
                    logger.info("calcResult ==> {}", cw);
                    logger.info("map ==> {}", map);
                    CompletableFuture<Integer> f = map.remove(cw.getId());
                    if (Objects.nonNull(f)) {
                        f.complete(cw.getResult());
                    }
                })
                .matchAny(other -> logger.warning("unknown message ==> {}", other))
                .build();

    }
}
