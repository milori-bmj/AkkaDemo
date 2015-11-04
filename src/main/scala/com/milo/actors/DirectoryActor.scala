package com.milo.actors

import akka.actor.{Props, ActorSelection, ActorPath, Actor}

/**
 * Created by MICHAEL on 26/10/2015.
 */
class DirectoryActor extends Actor {

  override def preStart()
  {
    val chinaDocsActor = context.actorOf(Props[CountryActor],"chinaDirectory")
    val rowDocsActor = context.actorOf(Props[CountryActor],"rowDirectory")

  }

  override def receive =
  {
    case "china" => val actorSelection = context.actorSelection("china/...")
                    actorSelection ! ""
    case "row" => val actorSelection = context.actorSelection("row/...")
      //actorSelection ! ""
  }

}
