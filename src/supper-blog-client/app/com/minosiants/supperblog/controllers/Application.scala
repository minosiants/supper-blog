package com.minosiants.supperblog.controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import play.api.data.validation.Constraints._
import views._
import com.minosiants.supperblog.common.Common
import com.minosiants.supperblog.common.UserToken
import com.minosiants.supperblog.common.model._
import com.minosiants.supperblog.common.util.Util._
import com.codahale.jerkson.Json._
import play.api.libs.json.Json._

case class SignUpData(username:String, email:String, password:String, reenterPassword:String)
case class SignInData(username:String, password:String)

object Application extends Controller with Common{
	
	val Home = Redirect(routes.Application.index(""))
	
	private def transformFormWithErrors[T](formWithErrors:Form[T])={
	  generate(Map(	"data"->formWithErrors.data,
			  		"errors"->formWithErrors.errors.map{e => Map("field"->e.key,"message"->e.message)})
			  )
	}
	
	def index(u:String)=Action{implicit request =>
	  println(request.uri)
	  Ok(html.main(request.uri))
		 
	}
	def signUpView()=Action{implicit request =>	  
		Ok(html.main(request.uri))
	
	}
	def signInView()=Action{implicit request =>
		
		Ok(html.main(request.uri))
	
	}
	def proxy(id:String)=Action{request=>
	  	println(request.uri)
		Ok(html.main(request.uri)) 
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
//	).verifying(	         
//	        "Passwords do not match", passwords => passwords._1 == passwords._2
//	      )
//	      
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
