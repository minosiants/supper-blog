package com.minosiants.supperblog.api.controllers
import com.minosiants.supperblog.common.UserToken
import play.api.mvc._
import play.api.mvc.Results._
import com.minosiants.supperblog.common.util.Util._
import java.util.Date
import com.minosiants.supperblog.common.model.UserProfile
import com.minosiants.supperblog.common.Common

trait Secured  {this:Common=>
  
	def username(request: RequestHeader) ={
	  println(request.headers)
	  request.cookies.get("user").map{c=>println(c);UserToken(c.value).username}.getOrElse(None)
	}

	def onUnauthorized(request: RequestHeader) = {
	  println(Unauthorized)
	  Unauthorized
	}
		
	
	def withUsername(f: => String => Request[AnyContent] => Result) = {
		Security.Authenticated(username, onUnauthorized) { user =>
			Action(request => f(user)(request))
		}
	}

	def withUser(f: => UserProfile => Request[AnyContent] => Result) = withUsername{username => implicit request =>
		userService.getUserProfile(username).map { user =>
			f(user)(request)
		}.getOrElse(onUnauthorized(request))
	}


}
