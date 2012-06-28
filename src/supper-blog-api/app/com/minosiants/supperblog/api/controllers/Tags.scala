package com.minosiants.supperblog.api.controllers
import play.api.mvc._
import views._
import play.api.data._
import play.api.data.Forms._
import play.api.data.validation.Constraints._
import play.api.libs.json.Json._
import com.codahale.jerkson.Json._
import com.minosiants.supperblog.model.Tag
import Headers._
import com.minosiants.supperblog.service.SupperBlog

object Tags extends Controller with ControllerCommon with SupperBlog{
  
	def get= Action {
	  withCors(
			  Ok(generate(postService.getTags))
		)
	}
}