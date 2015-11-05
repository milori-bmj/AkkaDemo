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
  def easterBehaviour:Receive =  {


    case SpanishHello  => println("Hola")
    case ItalianHello => println("")
    case FrenchHello => println("Bonjour")

    case PriceQuery ("Turkey",_) => println ("Out of Season")
    case PriceQuery ("Silly Jumpers",_) => println ("Out of Season")

    case Order(_, q) if q > 15 && q < 100 => println(" Postage discount 15%")
    case Order(_, q) if q >= 100 => println(" No Postage")
    case Order => println(" Full Postage")

    case SwapBehaviour("easter") => println("I'm already in the Easter Mood!")
    case SwapBehaviour("xmas") => become(xmasBehaviour)
    case SwapBehaviour("normal") => become(normalBehaviour)

    case _ => println("")

  }

  def normalBehaviour:Receive =  {

    case SpanishHello  => println("Hola")
    case ItalianHello => println("")
    case FrenchHello => println("Bonjour")

    case PriceQuery ("Turkey",_) => println ("Turkey Out of Season")
    case PriceQuery ("Silly Jumpers",_) => println ("Silly Jumpers Out of Season")
    case PriceQuery ("Easter Eggs",_) => println ("Easter Eggs Out of Season")
    case PriceQuery ("Bonnets",_) => println ("Bonnets Out of Season")

    case Order(_, q) if q > 15 && q < 100 => println(" Postage discount 15%")
    case Order(_, q) if q >= 100 => println(" No Postage")
    case Order => println(" Full Postage")

    case SwapBehaviour("normal") => println("I'm already Normal!")
    case SwapBehaviour("xmas") => become(xmasBehaviour)
    case SwapBehaviour("easter") => become(easterBehaviour)

    case _ => println("")

  }

  override def receive = {

      case msg: AnyRef => become(normalBehaviour)

  }

  def xmasBehaviour:Receive = {

    case SpanishHello  => println("felicadida")
    case ItalianHello => println("Xmas")
    case FrenchHello => println("Bon Fete")

    case PriceQuery ("Turkey",_) => println ("Turkey Available")
    case PriceQuery ("Silly Jumpers",_) => println ("Silly Jumpers Available")
    case PriceQuery ("Easter Eggs",_) => println ("Easter Eggs Out of Season")
    case PriceQuery ("Bonnets",_) => println ("Bonnets Out of Season")

    case Order(_, q) if q > 15 && q < 100 => println(" Postage discount 25%")
    case Order(_, q) if q >= 100 => println(" No Postage")
    case Order => println("  Postage discount 5%")

    case SwapBehaviour("xmas") => println("I'm already in the Xmas Mood!")
    case SwapBehaviour("easter") => become(easterBehaviour)
    case SwapBehaviour("normal") => become(normalBehaviour)

  }

}

