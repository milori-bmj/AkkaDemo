package com.milo.actors.demo5

import java.lang.StringBuilder

import akka.actor.{ActorRef, Actor}

/**
 * Created by MICHAEL on 11/11/2015.
 */

case class Message(msg:String)
case class Reply (reply:String, count:Int)
class SendReplyActor(replyActor: ActorRef) extends Actor{

  def sendMessage (msg:String): Unit =
  {
    replyActor ! Message(msg)
  }

  override def receive = {

    case Reply(m,0) => println(s"Received $m :  stopping dialogue ")
    case Reply(m,1) =>  println(s"Received $m :  Only one more message allowed!")
    case Reply(m,q) =>  println(s"Received $m : $q more messages allowed ")
    case s:String => sendMessage(s)
    case _ => println("Unknown message")
  }

}

class ReplyActor extends Actor {
  var quota = 5
  override def receive = {
    case Message(_) if quota < 1 => println("Out of calls!")
    case Message(m) => println(s"Received $m :") ; quota-= 1; sender ! Reply(new StringBuilder(m).reverse().toString() ,quota)

    case _ => println("Unknown message")
  }


}