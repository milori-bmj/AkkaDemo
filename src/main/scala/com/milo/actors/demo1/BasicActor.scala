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

    case 1 => println("I multiply or divide you and you don't change")
    case 42 => println("meaning of life!")
    case 9.58 => println("100m world record!")
    case "Peter" => println("Rock-y")
    case "trumpet" => println("Miles Davis")
    case "drums" => println("Neil Peart")

    case _ => println("I'm Something else")
  }

}
