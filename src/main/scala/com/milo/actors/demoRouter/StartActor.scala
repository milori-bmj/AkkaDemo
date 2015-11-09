package com.milo.actors.demoRouter

import akka.actor.Props
import akka.actor.ActorSystem

/**
  * Created by MICHAEL on 08/11/2015.
  */

object StartActor extends App
 {
   val system = ActorSystem("AkkaDemo")
   val workSprvsr = system.actorOf(Props[WorkSupervisor],"workSupervisor")

   workSprvsr ! StartWork

 }
