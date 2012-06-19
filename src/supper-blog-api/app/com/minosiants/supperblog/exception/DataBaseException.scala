package com.minosiants.supperblog.exception

case class DataBaseException(error:String="",exception:String="",message:String="") extends RuntimeException