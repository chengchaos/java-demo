package cn.chengchaos;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.testkit.TestActorRef;
import org.junit.Assert;
import org.junit.Test;

public class AkkaDemyDbTest {

    private static final ActorSystem system = ActorSystem.create();

    @Test
    public void akkaDemyDbTest() {

        TestActorRef<AkkaDemyDb> actorRef =
                TestActorRef.create(system, Props.create(AkkaDemyDb.class));

        actorRef.tell(new SetRequest("key", "value"), ActorRef.noSender());

        AkkaDemyDb akkaDemyDb = actorRef.underlyingActor();

        Assert.assertEquals("", akkaDemyDb.map.get("key"), "value");
    }
}
