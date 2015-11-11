package com.milo.actors.demo3

import akka.actor.Actor
import com.milo.actors.demo2._

/**
 * Created by MICHAEL on 04/11/2015.
 */
case class SwapBehaviour(mode:String)
class BehaviourChangeActor extends Actor
{


  import context._

  override def  preStart()
  {
    become(normalBehaviour)
  }

  def easterBehaviour:Receive =  {

    case SpanishHello  => println("Felices Pascuas")
    case ItalianHello => println("Buona Pasqua")
    case FrenchHello => println("joyeuses Pâques")

    case PriceQuery ("Turkey",_) => println ("Out of Season")
    case PriceQuery ("Silly Jumpers",_) => println ("Out of Season")

    case Order(_, q) if q > 15 && q < 100 => println(" Postage discount 18%")
    case Order(_, q) if q >= 100 => println(" No Postage")
    case o:Order => println(" Full Postage")

    case SwapBehaviour("easter") => println("I'm already in the Easter Mood!")
    case SwapBehaviour("xmas") => become(xmasBehaviour)
    case SwapBehaviour("normal") => become(normalBehaviour)

    case _ => println("No Match In Xmas Mode")

  }

  def normalBehaviour:Receive =  {

    case SpanishHello  => println("Hola")
    case ItalianHello => println("ciao")
    case FrenchHello => println("Bonjour")

    case PriceQuery ("Turkey",_) => println ("Turkey Out of Season")
    case PriceQuery ("Silly Jumpers",_) => println ("Silly Jumpers Out of Season")
    case PriceQuery ("Easter Eggs",_) => println ("Easter Eggs Out of Season")
    case PriceQuery ("Bonnets",_) => println ("Bonnets Out of Season")

    case Order(_, q) if q > 15 && q < 100 => println(" Postage discount 15%")
    case Order(_, q) if q >= 100 => println(" No Postage")
    case o:Order => println("  Postage discount 2%")

    case SwapBehaviour("normal") => println("I'm already Normal!")
    case SwapBehaviour("xmas") => become(xmasBehaviour)
    case SwapBehaviour("easter") => become(easterBehaviour)

    case _ => println("No Match In Normal Mode")

  }

  override def receive = Actor.emptyBehavior

  def xmasBehaviour:Receive = {

    case SpanishHello  => println("feliz Navidad")
    case ItalianHello => println("Buon Natale")
    case FrenchHello => println("Joyeux Noël")

    case PriceQuery ("Turkey",_) => println ("Turkey Available")
    case PriceQuery ("Silly Jumpers",_) => println ("Silly Jumpers Available")
    case PriceQuery ("Easter Eggs",_) => println ("Easter Eggs Out of Season")
    case PriceQuery ("Bonnets",_) => println ("Bonnets Out of Season")

    case Order(_, q) if q > 15 && q < 100 => println(" Postage discount 25%")
    case Order(_, q) if q >= 100 => println(" No Postage")
    case o:Order => println("  Postage discount 5%")

    case SwapBehaviour("xmas") => println("I'm already in the Xmas Mood!")
    case SwapBehaviour("easter") => become(easterBehaviour)
    case SwapBehaviour("normal") => become(normalBehaviour)
    case _ => println("No Match In Easter Mode")

  }

}

