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
import java.lang.reflect.Method

case class SignUpData(username:String, email:String, password:String, reenterPassword:String)
case class SignInData(username:String, password:String)

object Application extends Controller with Common{

	def index(u:String)=Action{implicit request =>	  
	  Ok(html.main(request.uri))
	}
	
	
	def templates(template:String)=Action{
	  renderView(template.replaceAll(".html", "")).map{Ok(_)}.getOrElse(NotFound)	  
	}
	
	private def renderView(template:String):Option[ play.api.templates.Html ] ={
		try {
		    val clazz : Class[ _ ] = Play.current.classloader.loadClass("views.html.templates."+template)
		    val render : Method = clazz.getDeclaredMethod("render")
		    return Some(render.invoke(clazz).asInstanceOf[ play.api.templates.Html ])
		}catch {	 
		    case ex : ClassNotFoundException => Logger.error("Html.renderDynamic() : could not find view " + template, ex)
		}
	  return None
    }
  
}
