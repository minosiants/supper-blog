package com.minosiants.supperblog.common.model
import com.mongodb.casbah.Imports._
import org.bson.types.ObjectId
import java.util.Date

trait Implicits {
  
  implicit def mongoDBObject_to_userProfile(dbo:MongoDBObject ):UserProfile = {  	  
    UserProfile(id=dbo.getAs[ObjectId]("_id").get.toString,
				name=dbo.getAsOrElse[String]("name",""),
				surname=dbo.getAsOrElse[String]("surname",""),
				username=dbo.getAsOrElse[String]("surname",""),
				email=dbo.getAsOrElse[String]("email",""),
				img=dbo.getAsOrElse[String]("img",""),
				bio=dbo.getAsOrElse[String]("bio",""),
				created=dbo.getAs[Date]("created").get,
				updated=dbo.getAs[Date]("updated").get)
  }
  implicit def dbObject_to_userProfile(dbo:DBObject ):UserProfile = {  	  
		  UserProfile(id=dbo.getAs[ObjectId]("_id").get.toString,
				  name=dbo.getAsOrElse[String]("name",""),
				  surname=dbo.getAsOrElse[String]("surname",""),
				  username=dbo.getAsOrElse[String]("username",""),
				  email=dbo.getAsOrElse[String]("email",""),
				  img=dbo.getAsOrElse[String]("img",""),
				  bio=dbo.getAsOrElse[String]("bio",""),
				  created=dbo.getAs[Date]("created").get,
				  updated=dbo.getAs[Date]("updated").get)
  }
  
  implicit def userProfile_to_dbObject(up:UserProfile):MongoDBObject = {
    
    MongoDBObject(	"_id"-> id(up.id),   
 					"name" -> up.name,
					"surname" -> up.surname,
					"username" -> up.username,
					"email" -> up.email,
					"img" -> up.img,
					"bio" -> up.bio,
					"created" -> up.created,
					"updated" -> up.updated)						
  } 	
  implicit def option_mongoDBObject_to_option_userProfile(dbo:Option[MongoDBObject] ):Option[UserProfile]=dbo.map{o=>mongoDBObject_to_userProfile(o)}
  implicit def option_dbObject_to_option_userProfile(dbo:Option[DBObject] ):Option[UserProfile]=dbo.map{o=>dbObject_to_userProfile(o)}
  
  private def id(id:String )=if (ObjectId.isValid(id)) new ObjectId(id) else new ObjectId()
  
   implicit def user_to_dbObject(up:User):MongoDBObject = {    
    MongoDBObject(	"_id"-> id(up.id),    					
					"username" -> up.username,
					"email" -> up.email,
					"password" -> up.password,					
					"created" -> up.created,
					"updated" -> up.updated)						
  }
  implicit def mongoDBObject_to_user(dbo:MongoDBObject ):User = {  	  
		  User(id=dbo.getAs[ObjectId]("_id").get.toString,				  
				  username=dbo.getAsOrElse[String]("username",""),
				  email=dbo.getAsOrElse[String]("email",""),				  
				  password=dbo.getAsOrElse[String]("password",""),
				  created=dbo.getAs[Date]("created").get,
				  updated=dbo.getAs[Date]("updated").get)
  }

}