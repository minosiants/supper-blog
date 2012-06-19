package  com.minosiants.supperblog.controllers

import play.api.mvc._
import play.api.mvc.Results._
import java.util.Date

import com.minosiants.supperblog.common.model.UserProfile
import com.minosiants.supperblog.common.Common
import com.minosiants.supperblog.common.UserToken

trait Secured  {this:Common=>
  
	def username(request: RequestHeader) = request.cookies.get("user").map{fetchUserName(_)}.getOrElse(None)

	def onUnauthorized(request: RequestHeader) = Unauthorized
	
	private def fetchUserName(cookie:Cookie):Option[String]={	
		UserToken(cookie.value).username
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

