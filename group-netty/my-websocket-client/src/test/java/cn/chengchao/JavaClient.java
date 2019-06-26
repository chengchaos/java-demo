package cn.chengchao;

import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.pattern.Patterns;
import com.typesafe.config.ConfigFactory;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.TimeUnit;

import static scala.compat.java8.FutureConverters.toJava;


public class JavaClient {


    public static void main(String[] args) throws Exception {


        ActorSystem actorSystem = ActorSystem.create("myAkkaDemo", ConfigFactory.load("application"));

        // akka.tcp://my-akka-demo@192.168.86.121:2552
        final String path = "" +
                "akka.tcp://myAkkaDemo@" +
                "192.168.86.121:2552" +
                "/user/oneDemo";


        System.out.println("path = "+ path);
//        final ActorSelection remoteActor = system.actorSelection(path);
//        System.out.println("actor = "+ remoteActor);

        final ActorRef lookup = actorSystem.actorOf(Props.create(LookupActor.class, path), "lookup");



        @SuppressWarnings("unchecked")
        final CompletionStage<Object> completionStage = toJava(Patterns.ask(lookup, "Call me", 10000L));
        final CompletableFuture<Object> cf = (CompletableFuture<Object>) completionStage;

        Object s = cf.get(10000L, TimeUnit.SECONDS);

        System.out.println("rds ==> "+ s);


    }
}
