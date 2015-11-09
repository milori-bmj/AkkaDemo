package com.milo.actors.demoRouter

import akka.actor.Props
import akka.actor.ActorSystem

/**
  * Created by MICHAEL on 08/11/2015.
  */

object StartActor extends App
 {
   val system = ActorSystem("AkkaDemo")
   val workSprvsrChina = system.actorOf(Props[WorkSupervisor],"ChinaWorkSupervisor")

   val workSprvsrRow = system.actorOf(Props[WorkSupervisor],"RowWorkSupervisor")


   val chinaUri = system.settings.config.getString("demo.xml.uri.china")
   val rowUri = system.settings.config.getString("demo.xml.uri.row")


  workSprvsrChina ! StartWork("china",chinaUri)
  workSprvsrRow ! StartWork("row",rowUri)


}
