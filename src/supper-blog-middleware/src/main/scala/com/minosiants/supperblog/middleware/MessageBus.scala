package com.minosiants.supperblog.middleware

import akka.event.ActorEventBus
import com.minosiants.supperblog.middleware.message.Message
import akka.event.LookupClassification

trait MessageBus extends ActorEventBus{
  type Event = Message
  type Classifier=String
}

class AppActorEventBus extends MessageBus with LookupClassification{
  
  protected def mapSize(): Int={
    10
  }

  protected def classify(event: Event): Classifier={
    event.header.channel
  }

  
  protected def publish(event: Event, subscriber: Subscriber): Unit={
    subscriber ! event
  }
}