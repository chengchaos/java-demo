package com.example.myscala002.akka;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.IllegalActorStateException;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.pf.ReceiveBuilder;
import akka.util.Timeout;
import org.hibernate.validator.internal.util.logging.LoggerFactory;
import scala.PartialFunction;
import scala.runtime.BoxedUnit;

import java.util.concurrent.TimeUnit;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * 功能的详细描述
 * </p>
 *
 * @author chengchaos[as]Administrator - 2019/4/1 0001 下午 5:35 <br />
 * @see 【相关类方法】
 * @since 1.1.0
 */
public class ManagerActor extends AbstractActor {

    private final LoggingAdapter logging = Logging.getLogger(context().system(), this);


    @Override
    public PartialFunction<Object, BoxedUnit> receive() {
        return ReceiveBuilder.create()

                .match(Message.Meeting.class, (m) -> {
                    logging.info("ManagerActor 收到 Meeting ==> {}", m);
                    sender().tell(new Message.Confirm("收到", self().path()), self());
                })

                .match(Message.DoAction.class, (a) -> {
                    logging.info("ManagerActor 收到 DoAction ==> {}", a);
                    ActorRef workerActor = context().actorOf(Props.create(WorkerActor.class), "worker");
                    workerActor.forward(a, context());
                })
                .matchAny(x -> logging.info("{} 不修电脑！", this))
                .build();
    }


}
