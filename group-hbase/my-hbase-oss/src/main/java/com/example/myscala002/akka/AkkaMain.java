package com.example.myscala002.akka;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * 功能的详细描述
 * </p>
 *
 * @author chengchaos[as]Administrator - 2019/4/1 0001 下午 5:16 <br />
 * @see 【相关类方法】
 * @since 1.1.0
 */
public class AkkaMain {

    public static void main(String[] args) {

        ActorSystem actorSystem = ActorSystem.create();

        ActorRef bossActor = actorSystem.actorOf(Props.create(BossActor.class));

        // Fitness industry has great prospects
        bossActor.tell(new Message.Business("健身产业前景广阔"), ActorRef.noSender());


    }
}
