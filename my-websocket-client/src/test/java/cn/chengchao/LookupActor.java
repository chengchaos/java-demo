package cn.chengchao;

import akka.actor.*;
import akka.japi.function.Procedure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import scala.concurrent.duration.Duration;

import static java.util.concurrent.TimeUnit.SECONDS;

public class LookupActor extends UntypedActor {

    private static Logger logger = LoggerFactory.getLogger(LookupActor.class);

    private final String path;
    private ActorRef calculator = null;

    public LookupActor(String path) {
        this.path = path;
        sendIdentifyRequest();
    }

    private void sendIdentifyRequest() {
        getContext().actorSelection(path).tell(new Identify(path), getSelf());
        getContext().system().scheduler().scheduleOnce(Duration.create(3, SECONDS), getSelf(),
                ReceiveTimeout.getInstance(), getContext().dispatcher(), getSelf());
    }

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof ActorIdentity) {
            calculator = ((ActorIdentity) message).getRef();
            if (calculator == null) {
                logger.info("Remote actor not available: {}", path);
            } else {
                getContext().watch(calculator);
//                getContext().become(active, true);
            }

        } else if (message instanceof ReceiveTimeout) {
            sendIdentifyRequest();

        } else {
            logger.info("Not ready yet");

        }
    }

    Procedure<Object> active = new Procedure<Object>() {
        @Override
        public void apply(Object message) {
            if (message instanceof String) {
                // send message to server actor
                calculator.tell(message, getSelf());

            } else if (message instanceof Terminated) {
                logger.info("Calculator terminated");
                sendIdentifyRequest();
                getContext().unbecome();

            } else if (message instanceof ReceiveTimeout) {
                // ignore

            } else {
                unhandled(message);
            }

        }
    };
}
