package com.milo.actors.demoRemote

import akka.actor.{Props, Actor, ActorSystem}
import com.milo.actors.demoRouter.StartWork

/**
 * Created by MICHAEL on 08/11/2015.
 */

object StartActor extends App
{
  val system = ActorSystem("AkkaDemo")
  val workSprvsr = system.actorOf(Props[WorkerRouterSupervisor])

  workSprvsr ! StartWork





}
