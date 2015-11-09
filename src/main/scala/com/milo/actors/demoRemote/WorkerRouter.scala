package com.milo.actors.demoRemote

import java.net.URL
import scala.xml.XML

import akka.actor.Actor

/**
 * Created by MICHAEL on 08/11/2015.
 */
case class ProcessMonograph(msg:String)
class WorkerRouterSupervisor extends Actor {

  override def receive = {

    case ProcessMonograph(monographName) =>
    case _ => println("hello world")
  }


  def getXML(monograph : String): Unit =
  {
    val monographXmlUri = s"http://cms.bmjgroup.com/best-practice/last-published/bp-zh-cn-live/$monograph/$monograph.xml"
    val xml = scala.xml.XML.load(new URL(monographXmlUri))

    xml.label match
    {
      case s if s.endsWith("-full") => println(s"$s:$monograph -full")
      case s if s.endsWith("-eval") => println(s"$s:$monograph -eval")
      case s if s.endsWith("-overview") => println(s"$s:$monograph -overview")
      case s if s.endsWith("-generic") => println(s"$s:$monograph -generic")
      case s => println(s"$s:$monograph -unknown element")
    }


  }
}
