package com.minosiants.supperblog.controllers

object App {

  def main(args: Array[String]): Unit = {
    val str="Hello world"
     println(str.toLowerCase.split("\\s").foldLeft("")((c,e)=>c+(if (c!="")  e.capitalize else e))) 
    
  }

}