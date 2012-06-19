package com.minosiants.supperblog.common
import com.minosiants.supperblog.common.util.Util._
import java.util.Date
import com.minosiants.supperblog.common.model.UserProfile

case class UserToken(username:Option[String],expirationDate:Int=daysInSeconds(30)) extends Common{
  
	def isExpired()=expirationDate > (new Date().getTime()-daysInMilliseconds(30))
	lazy val encodedUsername=encode64(username.getOrElse(""))
	def token=encodedUsername+"|"+expirationDate+"|"+signature(UserToken.SECRET,encodedUsername,""+expirationDate)
	def user:Option[UserProfile]=username.map{u=>userService.getUserProfile(u)}.getOrElse(None)
	
}

object UserToken{
  val SECRET="dmr@bR:_4AD[2V1];n/OMMrhAn`pkCpeLMU3H9TqNGc::7wm_[8JgjPGVSpV;=`l" //TODO    
  def apply(token:String)={
	  println(token)
	  val parts=token.split("\\|")
	  val encodedUsername:String=parts(0)
	  val expirationDate=parts(1).toInt	  
	  if(parts(2)!=signature(SECRET,encodedUsername,""+expirationDate)){
	    new UserToken(None,expirationDate)
	  }
	  new UserToken(Some(decode64(encodedUsername)),expirationDate)	  
  }
  
}