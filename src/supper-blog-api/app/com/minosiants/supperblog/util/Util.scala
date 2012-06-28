package com.minosiants.supperblog.util
import com.minosiants.supperblog.model.Tag

import  scala.util.Random
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec
import org.apache.commons.lang.StringEscapeUtils
import com.minosiants.supperblog.api.Config._
import org.apache.commons.codec.binary.Base64._
import org.apache.commons.codec.digest.DigestUtils
import org.bson.types.ObjectId
import scala.util.matching.Regex

object Util{
  
  
  val random = new Random()
  val chars="!#%+23456789:=?@ABCDEFGHJKLMNPRSTUVWXYZabcdefghijkmnopqrstuvwxyz";
  def uniqueId=new ObjectId().toString
  
  def parseHashtags(content:String):List[String]={
      val hashtagPattern = new Regex("""\s+#(\w+)""")
      hashtagPattern.findAllIn(content).toList.map{s=>s.replaceFirst("#", "")}    
  }
  
  def toList(str:String,separator:String=","):List[String]={
       str.split(separator).map(_.trim.toLowerCase).toList
  } 
  def digest(txt:String):String=DigestUtils.md5Hex(txt.getBytes("UTF-8"))
    
  def decode64(txt:String):String=new String(decodeBase64(txt))
  
  def encode64(txt:String):String=encode64(txt.getBytes())
 
  def encode64(bt:Array[Byte]):String=encodeBase64String(bt).stripLineEnd 
  
  def generateRandomStr(length:Int=8):String={       
    (for(x<-1 to length) yield chars.charAt(random.nextInt(64))).mkString("")    
  }
  def signature(parts:String*)={	  
	  val mac = Mac.getInstance("HmacSHA1");
	  mac.init(new SecretKeySpec(APP_SECRET.getBytes(),"HmacSHA1"));
	  encode64(parts.foldLeft(mac)((m,p) => {m.update(p.getBytes());  m }).doFinal())	 
  }

  
}
