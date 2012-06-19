package com.minosiants.supperblog.common.model

import java.util.Date

case class User(id:String,username:String,password:String,email:String, created:Date=new Date(),updated:Date=new Date())
case class UserProfile(	id:String,
						name:String,
						surname:String,
						username:String,
						email:String,
						img:String,
						bio:String,
						created:Date=new Date(),
						updated:Date=new Date())