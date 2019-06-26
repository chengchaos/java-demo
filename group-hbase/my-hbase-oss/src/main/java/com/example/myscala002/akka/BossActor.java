package com.example.myscala002.akka;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.pf.ReceiveBuilder;
import akka.pattern.Patterns;
import akka.util.Timeout;
import scala.PartialFunction;
import scala.concurrent.Future;
import scala.runtime.BoxedUnit;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * 功能的详细描述
 * </p>
 *
 * @author chengchaos[as]Administrator - 2019/4/1 0001 下午 5:19 <br />
 * @see 【相关类方法】
 * @since 1.1.0
 */
public class BossActor extends AbstractActor {

    private LoggingAdapter logging = Logging.getLogger(context().system(), this);

    private volatile int taskCount = 0;

    @Override
    public PartialFunction<Object, BoxedUnit> receive() {
        return ReceiveBuilder.create()
                .match(Message.Business.class, (b) -> {
                    logging.info("BossActor message ==> {}, 我们必须得干点啥，快！", b);
                    logging.info("BossActor self.path.address ==> {}", self().path().address());
                    List<ActorRef> managerActorList = new ArrayList<>(3);

                    for (int i = 1; i <= 3; i++){
                        ActorRef manager = context().actorOf(Props.create(ManagerActor.class), "manager_"+ i);
                        managerActorList.add(manager);
                    }

                    for (ActorRef manager: managerActorList){
                        Message.Meeting meeting = new Message.Meeting("来 22 楼上开会，有个重大利好！");
                        Future<Object> ask = Patterns.ask(manager, meeting, Timeout.apply(5, TimeUnit.SECONDS));

                        final CompletionStage<Object> cs = scala.compat.java8.FutureConverters.toJava(ask);

                        CompletableFuture<Object> cf = cs.toCompletableFuture();

                        cf.thenAcceptAsync(obj -> {
                            if (obj instanceof Message.Confirm) {
                                Message.Confirm c = (Message.Confirm) obj;
                                logging.info("收到 Confirm ==> {}", c);
                                //
                                logging.info("c.actorPath.parent.toString ==> {}",
                                        c.getActorPath().parent().toSerializationFormat());
                                //这里c.actorPath是绝对路径,你也可以根据相对路径得到相应的ActorRef
                                ActorSelection mgr = context().actorSelection(c.getActorPath());
                                logging.info("mgr is ==>{}", mgr);
                                mgr.tell(new Message.DoAction("开始干活！"), self());
                            }
                        });
                    }
                })
                .match(Message.Done.class, d -> {
                    taskCount += 1;
                    logging.info("BossActor: taskCount ==>; Done ==> {}", taskCount, d);
                    if (taskCount >= 3) {
                        logging.info("项目完成，我们开始分钱！");
                        context().system().terminate();
                    }
                })
                .matchAny(x -> logging.info("{} 不修电脑！", this))
                .build();

    }
}
