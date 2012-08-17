package com.minosiants.supperblog.api.controllers

import play.api.mvc._
import views._
import play.api.data._
import play.api.data.Forms._
import play.api.data.validation.Constraints._
import play.api.libs.json.Json._
import Headers._
import com.codahale.jerkson.Json._
import com.minosiants.supperblog.common.model.{UserProfile,User}
import com.minosiants.supperblog.model.{Post}
import com.minosiants.supperblog.service.SupperBlog
import play.api.Play.current
import play.api.Configuration
import com.minosiants.supperblog.common.UserToken
import play.api.libs.iteratee.Iteratee
import play.api.libs.iteratee.Enumerator
import com.minosiants.supperblog.middleware.Middleware.{subscriber,system}
import akka.actor.Props
import akka.actor.Actor
import com.minosiants.supperblog.middleware.message.Message
import com.minosiants.supperblog.middleware.Channel._
import com.minosiants.supperblog.middleware.Subscribe
import play.api.libs.json.JsValue
import com.minosiants.supperblog.middleware.Unsubscribe

object Posts extends Controller with ControllerCommon with Secured with SupperBlog{
	
	val postForm=Form(tuple(
			"id"->optional(text),
			 "title"->text,
			 "content"->text
			)
	)
  
	  
	def get(id:String) = Action {
	  withCors(
		postService.getPost(id).map(a=>Ok(generate(a))).getOrElse(NotFound)
		)
	}
	def config(key:String) = Action {	
		val config = play.api.Play.configuration
		Ok(config.getString(key).getOrElse("no value"))	
		
	}
	
	def filterByTags(tags:String) = Action { implicit request=>	  	  
	  withCors(	      
		Ok(generate(postService.getPostsWithTags(tags)))
		)
		
	}
	def find = Action {implicit request=>	  
	  withCors(
		Ok(generate(postService.getPosts()))
	  )
	}
	
	
	def create = withUser{user => implicit request=>
		withCors(
			postForm.bind(request.body.asJson.get).fold(
					errors=>BadRequest(generate(errorMessage(400,errors.errors))),
					
					post=>{
						val p=Post(author=user,title=post._2,content=post._3)
						println(p)
						Ok(generate(postService.createPost(p)))
					  }
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
	      post=>Ok(generate(postService.savePost(Post(id=post._1.getOrElse(""),author=user,title=post._2,content=post._3))))
	  ).withHeaders(ACCESS_CONTROL:_*)	  
	}
	def options2(id:String="")= Action {
	  withCors(
		Ok("")
		)
	}
	def options(id:String="")= Action {
		Ok("").withHeaders(ACCESS_CONTROL:_*) 		  
	}
	def delete(id:String)= withUsername { username => implicit request=>
	  postService.deletePost(id, username)
	  Ok.withHeaders(ACCESS_CONTROL:_*)		 
	}
	
	
	
	def postCreated= WebSocket.using[String] {implicit request => 
	  
	  
	  val out = Enumerator.imperative[String]( onStart = () => "")
	  val s = system.actorOf(Props(new Actor {
		  def receive = {
		  	case m: Message => out.push(generate(m.body))
		  }
	  }))
	  val in = Iteratee.consume[String]().mapDone{_=>
	    subscriber ! Unsubscribe(POST_CREATED,s)
	    println ("unsubscribe")
	  }
	subscriber ! Subscribe(POST_CREATED,s)
	  
	  (in, out)
	}
	
	
}