package com.minosiants.supperblog.api.controllers
import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import play.api.data.validation.Constraints._
import com.minosiants.supperblog.common.UserToken
import com.minosiants.supperblog.common.model.User
import com.minosiants.supperblog.common.util.Util._
import com.minosiants.supperblog.common.Common

object Session extends Controller with ControllerCommon with Common{
  
	def signIn=Action{implicit request =>
	  withCors(
	  	signInForm.bindFromRequest.fold(
			  formWithErrors =>badRequest(formWithErrors.errors),
			  user => Ok.withCookies(cookie(user.username))
	  	)
	  )
	}
	
	def signUp=Action{implicit request =>
	 withCors(
		signUpForm.bindFromRequest.fold(			  
			  formWithErrors =>badRequest(formWithErrors.errors),
			  user => Ok.withCookies(cookie(userService.createUser(user).username))			   			  
	    )
	 )
	}
	
	def signOut=Action{implicit request =>
	  withCors(
	      Ok.discardingCookies("user")
	  )
	}
	
	private def cookie(username:String)={
	  val ut=UserToken(Some(username))
	  Cookie("user", ut.token ,ut.expirationDate)
	}
	
	
	val signUpForm = Form[User](
	    mapping(
	      "username" -> nonEmptyText.verifying("This username is not available", username=>userService.userNameIsAvailable(username)),
	      "email" -> email,
	      "password" -> text(minLength = 6),	       
	      "confirm" -> text
	      	     
	    ){
	      (username,email,password,confirm)=>User(uniqueId,username,password,email)
	    }{
	      user=>Some(user.username,user.email,user.password,"")
	    }	    
	    	 
	)

	val signInForm = Form(
		mapping(
			"username" -> nonEmptyText,
			"password" -> nonEmptyText					
			)(SignInData.apply)(SignInData.unapply)
			.verifying("Username or password is not correct", 
						signIn=>userService.userExists(signIn.username,signIn.password)
			)
		)
}

case class SignUpData(username:String, email:String, password:String, reenterPassword:String)
case class SignInData(username:String, password:String)