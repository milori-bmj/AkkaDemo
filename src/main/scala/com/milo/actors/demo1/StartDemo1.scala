package com.milo.actors.demo1

import akka.actor.{Props, ActorSystem}

/**
 * Created by MICHAEL on 11/11/2015.
 */
object StartDemo1 extends App{
  val system = ActorSystem ("Demo1")
  val actor1 = system.actorOf(Props[BasicActor])

  actor1 ! 1
  actor1 ! "Akka Demo 1"
  actor1 ! 2.718281828459

  actor1 ! "drums"
  actor1 ! 'a'
}
