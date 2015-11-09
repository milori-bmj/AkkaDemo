package com.milo.actors.demo4

import akka.actor.{OneForOneStrategy, Props, Actor}
import akka.actor.SupervisorStrategy._
import scala.util.Random


case class MsgWhoAreYou();
case class MsgErrorEscalate()
case class MsgErrorResume()
case class MsgErrorStop()


object Supervision{

  case class ResumptionException (msg :String) extends RuntimeException (msg)
  case class EscalationException (msg :String) extends RuntimeException (msg)
  case class StopException (msg :String) extends RuntimeException (msg)
}
/**
 * Created by MICHAEL on 05/11/2015.
 */
class SupervisionActor extends Actor {
  val supervisedActor = context.actorOf(Props[SupervisedActor])

  override val supervisorStrategy = OneForOneStrategy()
  {

    case _ : Supervision.EscalationException => Escalate
    case _ : Supervision.ResumptionException => Resume
    case _ : Supervision.StopException => Stop
    case _ : Exception => Escalate

  }
  override def receive = {
    case "ww" => println("")
    case _ =>
  }
}

class SupervisedActor extends Actor {

  val superSupervisedActor = context.actorOf(Props[SuperSupervisedActor])

  override val supervisorStrategy = OneForOneStrategy()
  {
    case Supervision.EscalationException(msg) => println(msg);Escalate
    case Supervision.ResumptionException(msg) => println(msg);Resume
    case Supervision.StopException(msg) => println(msg);Stop
    case _ : Exception => Escalate
  }

  override def receive = {
    case "o" => println("")
    case "x" => println("")
    case _ =>
  }
}

class SuperSupervisedActor extends Actor {

  var state = 0
  override def receive = {
    case i:Int => println(10/i)
    case _ =>
  }
}