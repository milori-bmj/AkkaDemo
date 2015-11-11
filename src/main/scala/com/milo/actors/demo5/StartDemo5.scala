package com.milo.actors.demo5

import akka.actor.{ActorSystem, Props}

/**
 * Created by MICHAEL on 11/11/2015.
 */
object StartDemo5 extends App{
  val system = ActorSystem ("Demo5")
  val actorReply = system.actorOf(Props[ReplyActor])
  val actorSender = system.actorOf(Props(classOf[SendReplyActor],actorReply))
  actorSender ! "one"
  actorSender ! "two"
  actorSender ! "three"
  actorSender ! "four"
  actorSender ! "five"
  actorSender ! "six"
  actorSender ! "seven"

}
