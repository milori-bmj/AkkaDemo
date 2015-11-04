package com.milo.actors.demo2

import akka.actor.Actor

/**
 * Created by MICHAEL on 04/11/2015.
 */
case class SpanishHello()
case class ItalianHello()
case class FrenchHello()

case class PriceQuery(item:String,qty:Int)
case class Order(item:String,qty:Int)




class PatternMatchActor extends Actor
{
  def receive = {

    case SpanishHello  => println("Hola")
    case ItalianHello => println("")
    case FrenchHello => println("Bonjour")

    case PriceQuery ("Turkey",_) => println ("Out of Season")
    case PriceQuery ("Silly Jumpers",_) => println ("Out of Season")


    case Order(_, q) if q > 15 && q < 100 => println(" Postage discount 15%")
    case Order(_, q) if q >= 100 => println(" No Postage")
    case Order => println(" Full Postage")


    case _ => println("")

  }
}
