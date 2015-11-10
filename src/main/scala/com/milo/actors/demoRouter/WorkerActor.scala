package com.milo.actors.demoRouter

import java.io.{FileInputStream, OutputStreamWriter, InputStreamReader}
import java.net.URL

import akka.actor._
import akka.routing.FromConfig
import org.apache.commons._
import org.apache.http._
import org.apache.http.entity._
import org.apache.http.client._
import org.apache.http.client.methods._
import org.apache.http.impl.client._
import java.util.ArrayList
import org.apache.http.message.BasicNameValuePair
import org.apache.http.client.entity.UrlEncodedFormEntity
import scala.runtime.BoxedUnit

/**
 * Created by MICHAEL on 08/11/2015.
 */
case class ProcessMonograph(msg:String, dir:String, lang:String)

case class WriteToFile(json:String, fileName:String)

case class AddToESIndex(json:String, esUrl:String)

case class Monograph
(
 mgraphId :String,
 mgraphTitle:String,
 mgraphSynonym:Seq[String],
 mgraphCategory:Seq[String],
 mgraphType:String,
 mgraphAuthors:Seq[String],
 mgraphReviewers:Seq[String]
)


class WorkerActor extends Actor {

  override def receive = {

    case ProcessMonograph(monographName,dir,lang) => getXML(monographName,dir,lang)
    case _ => println("hello world")
  }


  def getXML(monograph : String, dir: String, lang:String): Unit =
  {
    val monographXmlUri = s"$dir\\$monograph"
    val xml = scala.xml.XML.load(new InputStreamReader(new FileInputStream(monographXmlUri), "UTF-8"))


    buildMonographJson(xml,lang)
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
         |"monograph-category":[${monographCategory.map("\"" + _ + "\"").mkString(",")}],
         |"monograph-type":"$monographType",
         |"monograph-synonyms":[${monographSynonym.map("\"" + _ + "\"").mkString(",")}],
         |"monograph-authors":[${monographAuthors.map("\"" + _ + "\"").mkString(",")}],
         |"monograph-peer-reviewers":[${monographReviewers.map("\"" + _ + "\"").mkString(",")}]}
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


    val esIndexUrl = context.system.settings.config.getString("demo.es.index.prefix")
    context.actorOf(Props(
      new Actor() {
        def receive = {
          case AddToESIndex(json,url) =>addToES(json,url); context.stop(self)
          case _ => println( "unknown message")
        }

        def addToES(jsonPayload:String,esUrl:String): Unit =
        {
          val post = new HttpPost(esUrl)
          val myEntity = new StringEntity(jsonPayload, ContentType.create("application/json", "UTF-8"));
          post.setEntity(myEntity)
          val httpclient = HttpClients.createDefault()
          val response = httpclient.execute(post)
        }
        context.self ! AddToESIndex(jsonStr,s"$esIndexUrl-$lang/monograph")
      }
    ))

    context.actorOf(Props(
      new Actor() {
        def receive = {
          case m:Monograph =>new MongoDao().insertMonograph(lang,m); context.stop(self)
          case _ => println( "unknown message")
        }

        context.self ! Monograph(
          monographId,
          monographTitle,
          monographSynonym,
          monographCategory,
          monographType,
          monographAuthors,
          monographReviewers
        )
      }
    ))
  //  var out_file = new java.io.FileOutputStream(s"$jsonDirName\\$monographId.json")
   //var out_stream = new OutputStreamWriter(out_file,"UTF-8")
   //out_stream.write(s"$monographId $monographTitle $monographCategory $monographType $monographSynonym")
   //out_stream.close
  }
}
