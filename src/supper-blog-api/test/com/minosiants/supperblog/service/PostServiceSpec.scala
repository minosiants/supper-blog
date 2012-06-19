package com.minosiants.supperblog.service

import org.specs2.mutable.Specification
import org.specs2.mutable.SpecificationWithJUnit
import com.minosiants.supperblog.model._
import com.minosiants.supperblog.common.model.UserProfile
//import play.api.test._
//import play.api.test.Helpers._
import org.bson.types.ObjectId
class PostServiceSpec extends SpecificationWithJUnit with SupperBlog{
	
	val _postService=postService
	
	"create a new post " in{
		val p=_postService.createPost(post())		   
	  // running(FakeApplication()) {
		   p.id must not beNull 
        
      //}

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

