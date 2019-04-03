package com.example.myscala002.akka;

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
 * @author chengchaos[as]Administrator - 2019/4/1 0001 下午 6:15 <br />
 * @see 【相关类方法】
 * @since 1.1.0
 */
public class WorkerActor  extends AbstractActor {

    private final LoggingAdapter logging = Logging.getLogger(context().system(), this);

    @Override
    public PartialFunction<Object, BoxedUnit> receive() {

        return ReceiveBuilder.create()
                .match(Message.DoAction.class, (a) -> {

                    logging.info("我收到任务单了 ==> {}", a);
                    sender().tell(new Message.Done("好了，我干完了。"), self());

                })
                .matchAny(x -> logging.info("{} 不修电脑！", this))
                .build();
    }
}
