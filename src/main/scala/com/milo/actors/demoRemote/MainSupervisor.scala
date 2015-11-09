package com.milo.actors.demoRemote

import akka.actor.{Props, Actor}
import akka.routing.FromConfig

/**
 * Created by MICHAEL on 08/11/2015.
 */
class MainSupervisor extends Actor
{

  override def receive = {
    case _ => println("hello world")
  }
}
