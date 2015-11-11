package com.milo.actors.demo3

import akka.actor.{ActorSystem, Props}
import com.milo.actors.demo2.SpanishHello

/**
 * Created by MICHAEL on 11/11/2015.
 */
object StartDemo3 extends App{

  val system = ActorSystem ("Demo3")
  val actor3 = system.actorOf(Props[BehaviourChangeActor])
  actor3 ! SpanishHello
  actor3 ! SwapBehaviour("easter")
  actor3 ! SpanishHello
  actor3 ! SwapBehaviour("easter")
  actor3 ! SwapBehaviour("xmas")
  actor3 ! SpanishHello

}
