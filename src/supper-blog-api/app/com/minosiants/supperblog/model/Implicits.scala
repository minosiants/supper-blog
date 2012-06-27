package com.minosiants.supperblog.model
import com.minosiants.supperblog.common.model.UserProfile
import com.minosiants.supperblog.common.model.{Implicits => CommonImplicits}
import com.mongodb.casbah.Imports._
import org.bson.types.ObjectId
import java.util.Date

trait Implicits extends CommonImplicits{
  
  
  implicit def mongoDBObject_to_post(dbo:MongoDBObject ):Post = {
    val tags=dbo.get("tags").filter(t=>t.isInstanceOf[BasicDBList]).map{e=>e.asInstanceOf[BasicDBList].toList.asInstanceOf[List[String]]}.getOrElse(Nil)
    Post(id=dbo.getAs[ObjectId]("_id").get.toString,  
			author=dbo.getAs[BasicDBObject]("author").get,
			title=dbo.getAsOrElse[String]("title",""),
			content=dbo.getAsOrElse[String]("content",""),
			tags=tags,
			created=dbo.getAs[Date]("created").get,
			updated=dbo.getAs[Date]("updated").get)
  }
  
  implicit def dbObject_to_post(dbo:DBObject ):Post = {
		  
		  Post(id=dbo.getAs[ObjectId]("_id").get.toString,  
				  author=dbo.getAs[DBObject]("author").get,
				  title=dbo.getAsOrElse[String]("title",""),
				  content=dbo.getAsOrElse[String]("content",""),
				  tags=dbo.as[BasicDBList]("tags").toList.asInstanceOf[List[String]],
				  created=dbo.getAs[Date]("created").get,
				  updated=dbo.getAs[Date]("updated").get)
  }
  implicit def option_mongodbObject_to_option_post(dbo:Option[MongoDBObject] ):Option[Post]=dbo.map{o=>mongoDBObject_to_post(o)}
  implicit def option_dbObject_to_option_post(dbo:Option[DBObject] ):Option[Post]=dbo.map{o=>dbObject_to_post(o)}
  
  implicit def list_dbObject_to_list_post(dbo:List[DBObject] ):List[Post]=dbo.map{o=>dbObject_to_post(o)}
  
  implicit def post_to_dbObject(p:Post):MongoDBObject={   
    
	val a:MongoDBObject=p.author
    MongoDBObject(	"_id"-> id(p.id),
					"author" -> a,
					"title" -> p.title,
					"content" -> p.content,
					"created" -> p.created,
					"tags" -> p.tags,			  								
					"updated" -> p.updated)
  }
  
  
  private def id(id:String )=if (ObjectId.isValid(id)) new ObjectId(id) else new ObjectId()																		
  
}


  trait ModelAdapter[T,B] {
	  def toDBObject(value: T): B
	  def fromDBObject(o:B):T
  }
  object ModelAdapter extends Implicits{	  
	  implicit object PostModelAdapter extends ModelAdapter[Post,MongoDBObject]{
		  def toDBObject(post: Post):MongoDBObject=post
				  def fromDBObject(o:MongoDBObject):Post=o
				  
	  }
	  implicit object UserProfileAdapter extends ModelAdapter[UserProfile,MongoDBObject]{
		  def toDBObject(user: UserProfile):MongoDBObject=user
				  def fromDBObject(o:MongoDBObject):UserProfile=o
				  
	  }
	  
  }
  
  
  