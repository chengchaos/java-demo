package com.example.springakkademo.actor;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.pattern.Patterns;
import akka.util.Timeout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import scala.Option;
import scala.Some;
import scala.compat.java8.FutureConverters;
import scala.concurrent.Future;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class ActorManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(ActorManager.class);

    public static final  ActorSystem actorSystem = ActorSystem.create("spring-akka");

    private ActorRef calcActor;

    private ActorRef masterActor;

    public ActorManager() {
        calcActor = actorSystem.actorOf(Props.create(CalcActor.class));
        masterActor = actorSystem.actorOf(Props.create(MasterActor.class));
    }



    private Timeout timeout() {

        return Timeout.apply(5000, TimeUnit.MILLISECONDS);
    }

    private AtomicInteger index = new AtomicInteger(0);


    public Option<Integer> calc2(Integer input) {
        CalcWrapper calcWrapper = new CalcWrapper(input);
        ActorRef ref = masterActor;

        masterActor.tell(calcWrapper, ActorRef.noSender());

        CompletableFuture<Integer> future = calcWrapper.getFuture();

        try {
            Integer result = future.get(70000, TimeUnit.MILLISECONDS);
            return Option.apply(result);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }

        return Option.empty();

    }


    public Option<Integer> calc(Integer input) {

        ActorRef ref = actorSystem.actorOf(Props.create(CalcActor.class));
        int curr = index.incrementAndGet();
        LOGGER.info("ref ==> {} ==> {}", ref, curr);


        Future<Object> ask = Patterns.ask(ref, input, timeout());

        CompletionStage<Object> objectCompletionStage = FutureConverters.toJava(ask);
        CompletableFuture<Object> future = objectCompletionStage.toCompletableFuture();

        try {
            Object obj = future.get(7000, TimeUnit.MILLISECONDS);

            return Some.apply(Integer.valueOf(obj.toString(), 10));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }

        return Option.empty();

    }

}
