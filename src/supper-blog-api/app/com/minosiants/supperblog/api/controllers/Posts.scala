package com.minosiants.supperblog.api.controllers

import play.api.mvc._
import views._
import play.api.data._
import play.api.data.Forms._
import play.api.data.validation.Constraints._
import play.api.libs.json.Json._
import Headers._
import views._
import com.codahale.jerkson.Json._

import com.minosiants.supperblog.common.model.{UserProfile,User}
import com.minosiants.supperblog.model.{Post}
import com.minosiants.supperblog.service.SupperBlog

import play.api.Play.current
import play.api.Configuration
import com.minosiants.supperblog.common.UserToken

object Posts extends Controller with ControllerCommon with Secured with SupperBlog{
	
	val postForm=Form(tuple(
			"id"->optional(text),
			 "title"->text,
			 "content"->text
			)
	)
  
	  
	def get(id:String) = Action {
		postService.getPost(id).map(a=>Ok(generate(a))).getOrElse(NotFound).withHeaders(ACCESS_CONTROL:_*)			  
	}
	def config(key:String) = Action {
		val config = play.api.Play.configuration
		Ok(config.getString(key).getOrElse("no value"))
		
	}
	def filterByTags(tags:String) = Action {	  
		Ok(generate(postService.getPostsWithTags(tags))).withHeaders(ACCESS_CONTROL:_*)
		
	}
	def find = Action {
	  withCors(
		Ok(generate(postService.getPosts()))
	  )
	}
	
	def create = withUser{user => implicit request=>
		withCors(
			postForm.bind(request.body.asJson.get).fold(
					errors=>BadRequest(generate(errorMessage(400,errors.errors))), 
					post=>Ok(generate(postService.createPost(Post(author=user,title=post._2,content=post._3))))
			)
		)  
	    
	 }	  
	  
	private def cookie(username:String)={
	  val ut=UserToken(Some(username))
	  Cookie("user1", ut.token ,ut.expirationDate)
	}
	def update(id:String) =withUser{user => implicit request=> 	  	  
	  	postForm.bind(request.body.asJson.get).fold(
	      errors=>BadRequest(generate(errorMessage(400,errors.errors))),
	      post=>Ok(generate(postService.updatePost(Post(id=post._1.getOrElse(""),author=user,title=post._2,content=post._3))))
	  ).withHeaders(ACCESS_CONTROL:_*)	  
	}
	def options2()= Action {
		Ok("").withHeaders(ACCESS_CONTROL:_*) 		  
	}
	def options(id:String="")= Action {
		Ok("").withHeaders(ACCESS_CONTROL:_*) 		  
	}
	def delete(id:String)= withUsername { username => implicit request=>
	  postService.deletePost(id, username)
	  Ok.withHeaders(ACCESS_CONTROL:_*)		 
	}
	
	
	//private def user:UserProfile=UserProfile("2334","kaspar","min","kas","kas@k.com","https://twimg0-a.akamaihd.net/profile_images/1355992238/photo_1__.JPG","my bio")
	
	
}