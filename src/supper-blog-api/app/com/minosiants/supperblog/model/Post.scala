package com.minosiants.supperblog.model
import com.minosiants.supperblog.common.model.UserProfile
import java.util.Date

case class Post(id:String="",author:UserProfile,title:String,content:String,tags:List[String]=Nil,created:Date=new Date(),updated:Date=new Date())

