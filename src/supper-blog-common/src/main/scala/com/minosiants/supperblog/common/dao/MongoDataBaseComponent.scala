package com.minosiants.supperblog.common.dao

import com.minosiants.supperblog.common.exception.DataBaseException
import com.minosiants.supperblog.common.model.QueryParams
import com.mongodb.casbah.Imports._
import com.minosiants.supperblog.common.Common

trait DataBase{
	def repository(name:String):Repository
}

trait Repository{
  def create(o:MongoDBObject):MongoDBObject
  def delete(criteria:MongoDBObject)
  def delete(id:String,criteria:MongoDBObject=MongoDBObject())
  def update(o:MongoDBObject,criteria:MongoDBObject)
  def save(o:MongoDBObject)
  def find(id:String):Option[DBObject]
  
  def find(query:MongoDBObject,params:QueryParams):List[DBObject]
  def find(params:QueryParams):List[DBObject]
  def findOne(query:Map[String,Any]):Option[DBObject]
  def distinct(key:String):List[Any]
}

class MongoDBRepository(colleaction:MongoCollection) extends Repository{
   
    type _MongoDBObject=com.mongodb.casbah.commons.MongoDBObject 															
    
    def create(o:MongoDBObject):MongoDBObject={        
    	checkResult(colleaction+=o)
    	o
    	
    }    
    def delete(criteria:MongoDBObject){
        colleaction.remove(criteria)
    }
    def delete(id:String,criteria:MongoDBObject){
    	delete(criteria+="_id"->new ObjectId(id))    	
    }
    def update(o:MongoDBObject,criteria:MongoDBObject){            
      checkResult(colleaction.update(o,criteria))      
    }
    def save(o:MongoDBObject){    	            
    	checkResult(colleaction.save(o))      
    }
    def find(id:String):Option[DBObject]={
    	colleaction.findOne(MongoDBObject("_id"->new ObjectId(id)))
    }
    def find(query:MongoDBObject,params:QueryParams)={      
      colleaction.find(query ++ ("created" $lt params.since))
      	.limit(params.limit)
      	.sort(MongoDBObject(params.orederBy->params.order.id)).toList      		      
    }
    
    def find(params:QueryParams)={
    	colleaction.find("created" $lt params.since)
      				.limit(params.limit)
      				.sort(MongoDBObject(params.orederBy->params.order.id))
      				.toList           
    }

    def findOne(query:Map[String,Any]):Option[DBObject]={
    	colleaction.findOne(query)
    }
    def distinct(key:String):List[Any]={
      colleaction.distinct(key).toList
    }
    private def checkResult(result:WriteResult){      
    	if(!result.getLastError.ok)    	      	    		
    		throw DataBaseException(error=result.getLastError.getErrorMessage)
    }
    
    
} 

trait MongoDataBaseComponent{this:Common=>
  
  val dataBase = new MongoDataBase() 
  

  class MongoDataBase extends DataBase{
	lazy val DB_HOST:String="127.0.0.1"
	lazy val DB_PORT=27017
	lazy val DB_NAME="supper-blog"
    val mongoDB = MongoConnection(DB_HOST,DB_PORT)(DB_NAME)
    def repository(name:String)=new MongoDBRepository(mongoDB(name))    
  }
}
