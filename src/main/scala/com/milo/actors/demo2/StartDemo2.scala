package com.milo.actors.demo2

import akka.actor.{ActorSystem, Props}

/**
 * Created by MICHAEL on 11/11/2015.
 */
object StartDemo2 extends App{
  val system = ActorSystem ("Demo2")
  val actor2 = system.actorOf(Props[PatternMatchActor])
  actor2 ! SpanishHello
  actor2 ! FrenchHello
  actor2 ! ItalianHello
  actor2 ! Order("Shoes",12)
  actor2 ! Order("Shoes",18)
  actor2 ! Order("Shoes",120)

  actor2 ! PriceQuery("Silly Jumpers",12)
  actor2 ! PriceQuery("Turkey",12)
  actor2 ! PriceQuery("Shoes",12)

}
