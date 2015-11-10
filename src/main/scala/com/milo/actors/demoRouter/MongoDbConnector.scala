package com.milo.actors.demoRouter

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
//import reactivemongo.api.commands.WriteResult
import play.api.libs.iteratee.Iteratee
import reactivemongo.api._
import reactivemongo.bson.BSONDocument
import reactivemongo.api.collections.default.BSONCollection
import scala.util.{Success,Failure}
/**
 * Created by MICHAEL on 03/07/2015.
 */
object MongoDbConnector{
  // gets an instance of the driver
  // (creates an actor system)
  val driver = new MongoDriver
  val connection = driver.connection(List("localhost"))
  import scala.concurrent.ExecutionContext.Implicits.global

  // Gets a reference to the database "plugin"
  val db = connection("monograph")

  // Gets a reference to the collection "acoll"
  // By default, you get a BSONCollection.
  val collection = db("monograph")


}
