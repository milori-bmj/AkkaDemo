package com.milo.actors.demo6

import akka.actor.{ActorSystem, Props}

/**
 * Created by MICHAEL on 11/11/2015.
 */
object StartDemo6 extends App{
  val system = ActorSystem("Demo6")
  val actorForwarder = system.actorOf(Props[ForwardingActor])
  val actorSender = system.actorOf(Props(classOf[SendingActor],actorForwarder))
  actorSender ! Request("google","What is the meaning of Life")
  actorSender ! Request("bing","What is the meaning of Life")
  actorSender ! Request("collins","What is the meaning of Life")

}
