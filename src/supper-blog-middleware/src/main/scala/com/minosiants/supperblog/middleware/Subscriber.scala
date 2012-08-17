package com.minosiants.supperblog.middleware

import akka.actor.ActorRef
import akka.actor.Actor

case class Subscribe(val channel:String, subscriber:ActorRef)
case class Unsubscribe(val channel:String, subscriber:ActorRef)

class Subscriber(implicit bus:MessageBus) extends Actor{
  def receive = {
	    case s: Subscribe => bus.subscribe(s.subscriber, s.channel)
	    case u: Unsubscribe => bus.unsubscribe(u.subscriber, u.channel)
  }
}