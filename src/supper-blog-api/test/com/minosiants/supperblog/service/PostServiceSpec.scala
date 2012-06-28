package com.minosiants.supperblog.service

import org.specs2.mutable.Specification
import org.specs2.mutable.SpecificationWithJUnit
import com.minosiants.supperblog.model._
import com.minosiants.supperblog.common.model.UserProfile
import org.bson.types.ObjectId
import org.junit.runner.RunWith
import org.specs2.runner.JUnitRunner

@RunWith(classOf[JUnitRunner])
class PostServiceSpec extends SpecificationWithJUnit with SupperBlog{
	
	val _postService=postService
	
//	"create a new post " in{
//		val p=_postService.createPost(post())		   
//		   p.id must not beNull 
//
//	}
//	
//	"get posts " in{
//		val p=_postService.getPosts()		   
//				println(p);				
//				p must not beNull 
//								
//				
//	}
//	"get tags " in{
//		val p=_postService.getTags()		   
//				println(p);				
//				p must not beNull 
//								
//				
//	}
	"get posts by tags " in{
		val p=_postService.getPostsWithTags("postp")		   
				println(p);				
		p must not beNull 
		
		
	}
//	"retrive a post" in{				
//		val p=postService.createPost(post())
//		postService.getPost(p.id) must beSome[Post] 
//	}
	private def post():Post={
	   val user=UserProfile(new ObjectId().toString, "kaspar","min","kas","kas@k.com","https://twimg0-a.akamaihd.net/profile_images/1355992238/photo_1__.JPG","my bio")
	   Post(author=user,title="my first title",content="my first content",tags=List("tag1","tag2"))
	}
}

