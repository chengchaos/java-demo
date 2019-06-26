package cn.chengchaos;

import akka.actor.*;
import akka.testkit.TestActorRef;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AkkaDemyDbTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(AkkaDemyDbTest.class);

    private static final ActorSystem system = ActorSystem.create();

    @Test
    public void akkaDemyDbTest() {

        /*
         * TestActorRef 提供同步 API
         * 并且允许我们访问其指向的 Actor
         */
        TestActorRef<AkkaDemyDb> actorRef =
                TestActorRef.create(system, Props.create(AkkaDemyDb.class), "AkkaDemyDb");

        actorRef.tell(new SetRequest("key", "value"), ActorRef.noSender());

        ActorPath path = actorRef.path();

        // path = akka://default/user/$$a
        LOGGER.info("path = {}", path);

        ActorSelection selection = system.actorSelection(path);

        selection.tell(new SetRequest("hello" , "程超"), ActorRef.noSender());


        AkkaDemyDb akkaDemyDb = actorRef.underlyingActor();

        Assert.assertEquals("", akkaDemyDb.map.get("key"), "value");

        System.err.println(akkaDemyDb.map);
    }
}
