package cn.futuremove.tsp.vehicle.control.business;

import akka.actor.ActorSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * https://doc.akka.io/docs/akka/2.5/futures.html
 */
public class ActorTaskAssigner  {

    private static final Logger LOGGER = LoggerFactory.getLogger(ActorTaskAssigner.class);


    public static final ActorSystem ACTOR_SYSTEM = ActorSystem.create("VehicleControlSystem");


}
