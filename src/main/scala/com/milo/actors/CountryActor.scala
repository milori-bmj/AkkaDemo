package com.milo.actors

import akka.actor.Actor

class CountryActor extends Actor{

  override def receive = {
    case _ => println("No Activity Yet")
  }

}