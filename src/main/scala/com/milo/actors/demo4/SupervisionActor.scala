package com.milo.actors.demo4

import akka.actor.{OneForOneStrategy, Props, Actor}
import akka.actor.SupervisorStrategy._
import scala.util.Random

/**
 * Created by MICHAEL on 05/11/2015.
 */
class SupervisionActor extends Actor {
  val supervisedActor = context.actorOf(Props[SupervisedActor])

  override def receive = {
    case "ww" => println("")
    case _ =>
  }
}

class SupervisedActor extends Actor {

  val superSupervisedActor = context.actorOf(Props[SuperSupervisedActor])

  override val supervisorStrategy = OneForOneStrategy()
  {

    case _ : NumberFormatException => Escalate

    case _ : Exception => Escalate
  }

  override def receive = {
    case "o" => println("")
    case "x" => println("")
    case _ =>
  }
}

class SuperSupervisedActor extends Actor {

  override def receive = {
    case i:Int => println(10/i)
    case _ =>
  }
}