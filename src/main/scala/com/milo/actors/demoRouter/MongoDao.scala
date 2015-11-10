package com.milo.actors.demoRouter

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
//import reactivemongo.api.commands.WriteResult
import scala.util.{Failure, Success}
import reactivemongo.api._
import reactivemongo.bson.BSONDocument
import reactivemongo.api.collections.default.BSONCollection
/**
 * Created by MICHAEL on 03/07/2015.
 */
class MongoDao{
  // gets an instance of the driver
  // (creates an actor system)

  def insertMonograph (lang:String, monograph:Monograph): Unit =
  {
    val collection = MongoDbConnector.db(lang)

    val insertDoc = BSONDocument(
      "monograph-id" -> monograph.mgraphId,
      "monograph-title" -> monograph.mgraphTitle,
      "monograph-synonym" -> monograph.mgraphSynonym,
      "monograph-category" -> monograph.mgraphCategory,
      "monograph-type" -> monograph.mgraphType,
      "monograph-authors" -> monograph.mgraphAuthors,
      "monograph-peer-reviewers" -> monograph.mgraphReviewers
    )

    val future = collection.insert(insertDoc)
    future.onComplete {
      case Failure(e) => throw e
      case Success(writeResult) =>
        println(s"successfully inserted document with result: $writeResult")
    }
  }
}
