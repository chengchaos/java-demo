package cn.chengchaos;

import akka.actor.ActorSelection;
import akka.actor.ActorSystem;
import akka.pattern.Patterns;

import java.util.concurrent.CompletionStage;

public class JClient {

    private final ActorSystem system = ActorSystem.create("LocalSystem");

    private final ActorSelection remoteDb;

    public JClient(String remoteAddress) {

        String path = "akka.tcp://akkademy@" + remoteAddress + "/user/akkademy-db";
        System.err.println("path = "+ path);
        this.remoteDb = system.actorSelection(path);
        System.err.println("remoteDb = "+ remoteDb);

    }

    public CompletionStage set(String key, String value) {

        return FutureHelper.toJava(Patterns
                .ask(remoteDb, new SetRequest(key, value), 4000));

    }

    public CompletionStage<Object> get(String key) {
        return FutureHelper.toJava(Patterns
                .ask(remoteDb, new GetRequest(key), 4000));
    }
}
