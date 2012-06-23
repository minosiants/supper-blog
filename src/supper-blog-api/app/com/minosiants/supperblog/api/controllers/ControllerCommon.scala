package com.minosiants.supperblog.api.controllers

import play.api._
import play.api.mvc._
import play.api.data._
import com.codahale.jerkson.Json._
import Headers._
trait ControllerCommon extends Results{
	def errorMessage(status:Int,errors:Seq[FormError])=Map("status"->status,"errors"->errors.map{e=>Map("key"->e.key,"message"->e.message)})
	
	def badRequest(errors:Seq[FormError])={
	  BadRequest(generate(errorMessage(400,errors)))
	  
	}
	
	def withCors(result:PlainResult)=result.withHeaders(ACCESS_CONTROL:_*)
	
}