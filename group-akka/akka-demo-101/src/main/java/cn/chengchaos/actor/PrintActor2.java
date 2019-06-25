package cn.chengchaos.actor;

import akka.actor.AbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.pf.ReceiveBuilder;
import scala.PartialFunction;
import scala.runtime.BoxedUnit;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * 功能的详细描述
 * </p>
 *
 * @author chengchaos[as]Administrator - 2019/1/22 0022 上午 11:11 <br />
 * @see 【相关类方法】
 * @since 1.1.0
 */
public class PrintActor2 extends AbstractActor {

    private final LoggingAdapter logging = Logging.getLogger(context().system(), this);

    @Override
    public PartialFunction<Object, BoxedUnit> receive() {
        return ReceiveBuilder.match(String.class, msg -> logging.info("{} ==> receive .. {}",
                Thread.currentThread().getName(), msg))
                .matchAny(msg -> logging.warning("unknown message : {}", msg))
                .build();
    }
}
