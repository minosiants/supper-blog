package com.minosiants.supperblog.util

object TextUtil {
  def camelify(str:String):String=str.toLowerCase.split("\\s").foldLeft("")((result,el)=>result+(if (result!="")  el.capitalize else el))  
}