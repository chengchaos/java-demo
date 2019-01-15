package cn.chengchaos.actor;

import akka.actor.AbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.pf.ReceiveBuilder;
import cn.chengchaos.FutureDemo;
import scala.PartialFunction;
import scala.runtime.BoxedUnit;

import java.util.concurrent.CompletableFuture;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * 功能的详细描述
 * </p>
 *
 * @author chengchao - 19-1-11 下午3:55 <br />
 * @see [相关类方法]
 * @since 1.0.0
 */
public class SecondActor extends AbstractActor {

    private final LoggingAdapter logger = Logging.getLogger(context().system(), this);


    @Override
    public PartialFunction<Object, BoxedUnit> receive() {
        return ReceiveBuilder.create()
                .match(Integer.class, input -> {
                    FutureDemo.waitSomeTime(1);
                    CompletableFuture<Integer> future = FutureDemo.map.get("chaos");

//                    sender().tell(input + 10, self());
//                    FutureDemo.map.get("chaos").complete(input + 11111);

                    future.cancel(true);

                })
                .matchAny(other -> logger.error("unknown message"))
                .build();
    }
}
