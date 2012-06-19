package com.minosiants.supperblog.common.service

import com.minosiants.supperblog.common.model.UserProfile
import com.minosiants.supperblog.common.Common
import com.minosiants.supperblog.common.util.Util.digest
import com.minosiants.supperblog.common.model.User

trait UserService{
  def getUserProfile(username:String):Option[UserProfile]  
  def userNameIsAvailable(username:String):Boolean
  def userExists(username:String,password:String):Boolean
  def createUser(user:User):User
}

trait UserServiceComponent {this:Common=>
	val userService=new UserServiceImpl 
  
	class UserServiceImpl extends UserService{
		lazy val repository=dataBase.repository("users")
		
		def getUserProfile(username:String):Option[UserProfile]={
			repository.findOne(Map("username"->username))			
		}
		def userNameIsAvailable(username:String):Boolean={
		  getUserProfile(username)==None
		}
		def userExists(username:String,password:String):Boolean={
		  repository.findOne(Map("username"->username,"password"->digest(password)))!=None
		}
		def createUser(user:User):User={
		  repository.create(user.copy(password=digest(user.password)))
		}
		
		
  }
	
}