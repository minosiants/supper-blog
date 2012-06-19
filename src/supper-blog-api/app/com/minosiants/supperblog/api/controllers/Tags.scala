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

object Tags extends Controller {
  
	def get= Action {
		Ok(generate(List(Tag("tag1"),Tag("tag2"),Tag("tag3"),Tag("tag5"),Tag("tag4"),Tag("tag6")))).withHeaders(ACCESS_CONTROL:_*)
	  
	}
}