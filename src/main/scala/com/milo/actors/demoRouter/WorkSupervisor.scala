package com.milo.actors.demoRouter

import akka.actor.{Props, ActorSystem, Actor}
import akka.routing.FromConfig

/**
 * Created by MICHAEL on 08/11/2015.
 */

case class StartWork(lang:String, uri:String)
class WorkSupervisor extends Actor{

  //val system = ActorSystem("RouterDemo")
  val workerRouter = context.actorOf(FromConfig.props(
    Props[WorkerActor]),
    "poolRouter")




  override def receive =
  {
    case StartWork(lang, uri) => println(s"start $lang Monographs!"); listMonographs(uri).foreach(m => {workerRouter ! ProcessMonograph(m,uri,lang)})

    case _ => println("unknown instruction")
  }

  def listMonographs(uri :String):Iterator[String] =
  {
    import scala.io.Source
    val html = Source.fromURL(uri)
    val s = html.mkString
    val pattern = """<a href=.[0-9]*""".r
    val digitsPattern = """[0-9]*""".r
    val monos = pattern.findAllIn(s).flatMap{digitsPattern.findAllIn(_)}
    monos.filter((st:String) =>(st.length > 0))
  }

}
