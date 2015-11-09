package com.milo.actors.demoRouter

import akka.actor.{Props, ActorSystem, Actor}
import akka.routing.FromConfig

/**
 * Created by MICHAEL on 08/11/2015.
 */

case object StartWork
class WorkSupervisor extends Actor{

  //val system = ActorSystem("RouterDemo")
  val workerRouter = context.actorOf(FromConfig.props(
    Props[WorkerActor]),
    "poolRouter")



  override def receive =
  {
    case StartWork => println("start working!"); listMonographs().foreach(m => {workerRouter ! ProcessMonograph(m)})
    case _ => println("unknown instruction")
  }

  def listMonographs():Iterator[String] =
  {
    import scala.io.Source
    val html = Source.fromURL("http://cms.bmjgroup.com/best-practice/last-published/bp-zh-cn-live/")
    val s = html.mkString
    val pattern = """<a href=.[0-9]*""".r
    val digitsPattern = """[0-9]*""".r
    val monos = pattern.findAllIn(s).flatMap{digitsPattern.findAllIn(_)}
    //monos.filter((st:String) =>(st.length > 0)).foreach(println(_))
    monos.filter((st:String) =>(st.length > 0))
  }

}
