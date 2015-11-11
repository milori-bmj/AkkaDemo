package com.milo.actors.demo6

import akka.actor.{ActorRef, Props, Actor}

/**
 * Created by MICHAEL on 11/11/2015.
 */

case class Request(service:String,msg:String)
case class Response(resp:String)
class ForwardingActor extends Actor
{
  val google = context.actorOf(Props[GoogleSearchActor])
  val bing = context.actorOf(Props[BingSearchActor])
  val dictionary = context.actorOf(Props[PlainDictionarySearchActor])
  override def  receive = {
    case Request("google",m) => google forward m
    case Request("bing",m) => bing forward m
    case Request("collins",m) => dictionary forward m

  }

}

class SendingActor (forwardingActor:ActorRef) extends Actor
{
  override def  receive = {
    case req:Request => println (s"Request received $req"); forwardingActor ! req
    case resp: Response => println (s"Message received $resp")
  }
}

class GoogleSearchActor extends Actor
{
  val service = "Google"
  override def  receive = {
    case s:String => sender ! Response("4 * 10 + 2")

  }
}

class BingSearchActor extends Actor
{
val service = "Bing"
  override def  receive = {
    case s:String => sender ! Response("20 + 10 + 10 + 2")

  }
}

class PlainDictionarySearchActor extends Actor
{
  val service = "Collins"
  override def  receive = {
    case s:String => sender ! Response("42")

  }
}
