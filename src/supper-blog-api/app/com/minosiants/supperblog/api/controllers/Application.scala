package com.minosiants.supperblog.api.controllers

import play.api._
import play.api.mvc._
import views._
object Application extends Controller {
	
	def index=Action{
		Ok(views.html.index("")) 
	}

}
