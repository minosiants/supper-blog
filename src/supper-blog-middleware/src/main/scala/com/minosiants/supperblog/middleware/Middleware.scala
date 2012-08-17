package com.minosiants.supperblog.middleware

import akka.actor.ActorSystem
import akka.actor.Props

object Middleware {
	val system = ActorSystem("middleware")
	implicit val bus:MessageBus=new AppActorEventBus
	val publisher=system.actorOf(Props(new Publisher),name="publisher")
	val subscriber=system.actorOf(Props(new Subscriber),name="subscriber") 
	
}