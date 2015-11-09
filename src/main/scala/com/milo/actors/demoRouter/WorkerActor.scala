package com.milo.actors.demoRouter

import java.io.{FileInputStream, OutputStreamWriter, InputStreamReader}
import java.net.URL

import akka.actor._
import akka.routing.FromConfig

import scala.runtime.BoxedUnit

/**
 * Created by MICHAEL on 08/11/2015.
 */
case class ProcessMonograph(msg:String, dir:String, lang:String)

case class WriteToFile(json:String, fileName:String)

class WorkerActor extends Actor {

  override def receive = {

    case ProcessMonograph(monographName,dir,lang) => getXML(monographName,dir,lang)
    case _ => println("hello world")
  }


  def getXML(monograph : String, dir: String, lang:String): Unit =
  {
    val monographXmlUri = s"$dir\\$monograph"
    val xml = scala.xml.XML.load(new InputStreamReader(new FileInputStream(monographXmlUri), "UTF-8"))

    xml.label match
    {
      case s if s.endsWith("-full") =>   buildMonographJson(xml,lang)

      case s if s.endsWith("-eval") => println(s"$s:$monograph -eval")
      case s if s.endsWith("-overview") => println(s"$s:$monograph -overview")
      case s if s.endsWith("-generic") => println(s"$s:$monograph -generic")
      case s => println(s"$s:$monograph -unknown element")
    }

  }

  def buildMonographJson (mGphXml : scala.xml.Elem, lang:String): Unit =
  {
    val jsonDirName = s"C:\\akkademo\\$lang\\json"
    val monographId = mGphXml \\ ("@dx-id") text
    val monographTitle =  mGphXml \\ "monograph-info" \ "title" text
    val monographSynonym = (mGphXml \\ "monograph-info" \ "topic-synonyms"  \ "synonym") map {_ text}
    val monographCategory = (mGphXml \\ "monograph-info" \ "categories" \ "category" ) map {_ text}
    val monographType = mGphXml.label.substring("monograph-".length)
    val monographAuthors = (mGphXml \\ "monograph-info" \ "authors" \  "author" \ "name" ) map {_ text}
    val monographReviewers = (mGphXml \\ "monograph-info" \ "peer-reviewers" \  "peer-reviewer" \ "name" ) map {_ text}


    println( s"$monographId $monographTitle $monographCategory $monographType $monographSynonym $monographAuthors $monographReviewers" )


    val jsonStr =
      s"""{"monograph-id":"$monographId",
         |"monograph-title":"$monographTitle",
         |"monograph-category":"$monographCategory",
         |"monograph-type":"$monographType",
         |"monograph-synonyms":"[${monographSynonym.mkString(",")}]",
         |"monograph-authors":"[${monographAuthors.mkString(",")}]",
         |"monograph-peer-reviewers":"[${monographReviewers.mkString(",")}]"
       """.stripMargin

    val actorWriter = context.actorOf(Props(

     new Actor() {


      def receive = {
        case WriteToFile(json,file) => writeJsonTofile(json,file); context.stop(self)
        case _ => println( "unknown message")
      }

      def writeJsonTofile(json:String,fileName:String): Unit =
      {
        var out_file = new java.io.FileOutputStream(fileName)
        var out_stream = new OutputStreamWriter(out_file,"UTF-8")
        out_stream.write(json)
        out_stream.close
      }


    }

    ))

   actorWriter ! WriteToFile(jsonStr,s"$jsonDirName\\$monographId.json")


  //  var out_file = new java.io.FileOutputStream(s"$jsonDirName\\$monographId.json")
   //var out_stream = new OutputStreamWriter(out_file,"UTF-8")
   //out_stream.write(s"$monographId $monographTitle $monographCategory $monographType $monographSynonym")
   //out_stream.close
  }
}
