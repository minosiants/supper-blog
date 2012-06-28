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
  def savePost(post:Post):Post
  def getPost(id:String):Option[Post]  
  def getPostsWithTags(tags:String,params:QueryParams=QueryParams()):List[Post]
  def getPosts(params:QueryParams=QueryParams()):List[Post]
  def getTags():List[Tag]
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
     def savePost(post:Post)={
       val p=post.copy(tags=parseHashtags(post.content),updated=new Date())
       repository.find(post.id)
       	.filter(_.author.username==post.author.username)
       	.map{_=>repository.save(p)}
       post
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
     def getTags()={
        repository.distinct("tags").map{t=>Tag(t.asInstanceOf[String])}
        
     }
  } 
}


