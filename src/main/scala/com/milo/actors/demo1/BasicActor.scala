package com.milo.actors.demo1

import akka.actor.Actor

/**
 * Created by MICHAEL on 04/11/2015.
 */
class BasicActor extends Actor{

  override def receive = {
    case x:String => println("I'm a String")
    case x:Int => println("I'm an Integer")
    case x:Double => println("I'm a Double")
  }

}
