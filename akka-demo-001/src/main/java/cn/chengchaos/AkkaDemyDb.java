package cn.chengchaos;

import akka.actor.AbstractActor;
import akka.actor.Status;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.pf.ReceiveBuilder;
import scala.PartialFunction;
import scala.runtime.BoxedUnit;

import java.util.HashMap;
import java.util.Map;

public class AkkaDemyDb extends AbstractActor {

    protected final LoggingAdapter logger = Logging.getLogger(context().system(), this);

    protected final Map<String, String> map = new HashMap<>();

    /**
     * 在构造函数中调用 receive 方法。
     * 那个接收 ReceiveBuilder 的方法。
     */
    public AkkaDemyDb() {
        PartialFunction<Object, BoxedUnit> pf = ReceiveBuilder
                .match(SetRequest.class, message -> {
                    logger.info("Received Set request: {}", message);
                    map.put(message.getKey(), message.getValue().toString());
                    sender().tell(new Status.Success(message.getKey()), self());
                })
                .match(GetRequest.class, message -> {
                    logger.info("Received Get request: {}", message);
                    String value = map.get(message.getKey());
                    Object response = (value != null)
                            ? value
                            : new Status.Failure(new KeyNotFoundException(message.getKey()));
                    sender().tell(response, self());
                })
                .matchAny(other -> logger.info("Received unknown message: {}", other))
                .build();
        super.receive(pf);
    }
}
