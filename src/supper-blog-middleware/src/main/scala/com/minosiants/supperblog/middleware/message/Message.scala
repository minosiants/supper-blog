package com.minosiants.supperblog.middleware.message

import java.util.Date
import java.util.UUID

case class Header(val id:String=UUID.randomUUID().toString(),val timestamp: Long=new Date().getTime(),val channel:String) 
case class Message(val header:Header, val body:AnyRef)
object Message{
  def apply(channel:String,body:AnyRef )=new Message(Header(channel=channel),body)
}
case class PostCreated(val post:AnyRef)
case class PostUpdated(val post:AnyRef)
case class PostDeleted(val postId:AnyRef)