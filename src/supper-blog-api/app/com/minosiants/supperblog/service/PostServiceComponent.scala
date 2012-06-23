package com.minosiants.supperblog.service

import java.util.Date
import com.minosiants.supperblog.api.Config._
import com.minosiants.supperblog.model._
import com.minosiants.supperblog.common.model.QueryParams
import com.minosiants.supperblog.exception._
import com.minosiants.supperblog.common.util.Util._
import com.mongodb.casbah.Imports._

trait PostService{
  def createPost(post:Post):Post
  def deletePost(id:String,username:String)
  def updatePost(post:Post):Post
  def getPost(id:String):Option[Post]  
  def getPostsWithTags(tags:String,params:QueryParams=QueryParams()):List[Post]
  def getPosts(params:QueryParams=QueryParams()):List[Post]
}


trait PostServiceComponent extends Implicits{this:SupperBlog=>
  
  val  postService=new PostServiceImpl
  
  class PostServiceImpl extends PostService{
    
	 lazy val repository=dataBase.repository("posts")
    
	 def createPost(post:Post):Post={	   
       repository.create(post.copy(id=uniqueId,tags=parseHashtags(post.content)))  
       
     }
     def deletePost(id:String,username:String){
       repository.delete(id,MongoDBObject("author.username" -> username))
     }
     def updatePost(post:Post)={
       val p=post.copy(tags=parseHashtags(post.content),updated=new Date())
       repository.update(p,MongoDBObject("username"->post.author.username))
       p
     }
     def getPost(id:String):Option[Post]={
       repository.find(id)
     }
     def getPostsWithTags(tags:String,params:QueryParams)={       
       repository.find("tags" $in toList(tags),params)
     }
     def getPosts(params:QueryParams=QueryParams())={
       repository.find(params)
     }
     
  } 
}


