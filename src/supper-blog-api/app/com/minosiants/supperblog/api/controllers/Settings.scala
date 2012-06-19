package com.minosiants.supperblog.api.controllers
import play.api.mvc._
import com.codahale.jerkson.Json._
import play.api.data.Forms._
import play.api.data._
import com.minosiants.supperblog.common.model.UserProfile
import Headers._

object Settings extends Controller {
  
	val profileForm=Form(tuple(
			"name"->text,
			 "surname"->text,
			 "username"->text,			 			 
			 "bio"->text
			)
	)
	def getProfile=Action{	  
	  Ok(generate(user)).withHeaders(ACCESS_CONTROL:_*)
	}
	def saveProfile=Action(parse.json){request=>
	  val pf=profileForm.bind(request.body)
	  (pf.errors match {	  	  
	  	case Nil => Ok(generate(doSaveProfile(pf)))
	  	case errors => BadRequest(generate(errorMessage(400,errors)))
	  }).withHeaders(ACCESS_CONTROL:_*)	  	   		 
	}
	private def doSaveProfile(pf:Form[(String, String, String, String)]):UserProfile=user //TODO 
	private def errorMessage(status:Int,errors:Seq[FormError])=Map("status"->status,"errors"->errors)
	private def user:UserProfile=UserProfile("2334","kaspar","min","kas","kas@k.com","https://twimg0-a.akamaihd.net/profile_images/1355992238/photo_1__.JPG","my bio")
}