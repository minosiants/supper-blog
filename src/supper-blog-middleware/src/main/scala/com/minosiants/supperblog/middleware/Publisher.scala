package com.minosiants.supperblog.middleware

import akka.actor.Actor
import com.minosiants.supperblog.middleware.message.Message
import com.minosiants.supperblog.middleware.message._

class Publisher(implicit bus:MessageBus) extends Actor{
  def receive = {
	    case post: PostCreated => bus.publish(Message(Channel.POST_CREATED,post.post))
	    case post: PostDeleted => bus.publish(Message(Channel.POST_DELETED,post.postId))
	    case post: PostUpdated => bus.publish(Message(Channel.POST_UPDATED,post.post))
  }
}
