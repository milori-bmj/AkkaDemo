package com.milo.actors.demo4

import akka.actor.Actor

import scala.util.Random

/**
 * Created by MICHAEL on 05/11/2015.
 */
class SupervisionActor extends Actor {

  override def receive = {
    case "ww" => println("")
    case _ =>
  }
}

class SupervisedActor extends Actor {

  override def receive = {
    case "ww" => println("")
    case _ =>
  }
}

class SuperSupervisedActor extends Actor {

  override def receive = {
    case i:Int => println(10/i)
    case _ =>
  }
}