package com.milo.actors.demoRouter

import java.io.{OutputStreamWriter, InputStreamReader}
import java.net.URL

import akka.actor.{Actor, Props}
import akka.routing.FromConfig

/**
 * Created by MICHAEL on 08/11/2015.
 */
case class ProcessMonograph(msg:String)

class WorkerActor extends Actor {

  override def receive = {

    case ProcessMonograph(monographName) => getXML(monographName)
    case _ => println("hello world")
  }


  def getXML(monograph : String): Unit =
  {
    val monographXmlUri = s"http://cms.bmjgroup.com/best-practice/last-published/bp-zh-cn-live/$monograph/$monograph.xml"
    val xml = scala.xml.XML.load(new InputStreamReader(new URL(monographXmlUri).openStream(), "UTF-8"))

    xml.label match
    {
      case s if s.endsWith("-full") =>   buildMonographJson(xml)

      case s if s.endsWith("-eval") => println(s"$s:$monograph -eval")
      case s if s.endsWith("-overview") => println(s"$s:$monograph -overview")
      case s if s.endsWith("-generic") => println(s"$s:$monograph -generic")
      case s => println(s"$s:$monograph -unknown element")
    }

  }

  def buildMonographJson (mGphXml : scala.xml.Elem): Unit =
  {
    val jsonDirName = s"C:\\akkademo\\json"
    val monographId = mGphXml \\ ("@dx-id") text
    val monographTitle =  mGphXml \\ "monograph-info" \ "title" text
    val monographSynonym = (mGphXml \\ "monograph-info" \ "topic-synonyms"  \ "synonym") map {_ text}
    val monographCategory = (mGphXml \\ "monograph-info" \ "categories" \ "category" ) map {_ text}
    val monographType = mGphXml.label.substring("monograph-".length)
    val monographAuthors = (mGphXml \\ "monograph-info" \ "authors" \  "author" \ "name" ) map {_ text}
    val monographReviewers = (mGphXml \\ "monograph-info" \ "peer-reviewers" \  "peer-reviewer" \ "name" ) map {_ text}


    println( s"$monographId $monographTitle $monographCategory $monographType $monographSynonym $monographAuthors $monographReviewers" )



    var out_file = new java.io.FileOutputStream(s"$jsonDirName\\$monographId.json")
    var out_stream = new OutputStreamWriter(out_file,"UTF-8")
    out_stream.write(s"$monographId $monographTitle $monographCategory $monographType $monographSynonym")
    out_stream.close
  }
}
