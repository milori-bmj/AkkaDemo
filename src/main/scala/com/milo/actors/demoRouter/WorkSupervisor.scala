package com.milo.actors.demoRouter

import java.io.File

import akka.actor.{Props, ActorSystem, Actor}
import akka.routing.FromConfig

/**
 * Created by MICHAEL on 08/11/2015.
 */

case class StartWork(lang:String, dir:String)
class WorkSupervisor extends Actor{

  //val system = ActorSystem("RouterDemo")
  val workerRouter = context.actorOf(FromConfig.props(
    Props[WorkerActor]),
    "poolRouter")




  override def receive =
  {
    case StartWork(lang, dir) => println(s"start $lang Monographs!"); listMonographs(dir).foreach(m => {workerRouter ! ProcessMonograph(m,dir,lang)})

    case _ => println("unknown instruction")
  }

  def listMonographs(dir :String):Array[String] =
  {

    new File(dir).list()
  }

}
