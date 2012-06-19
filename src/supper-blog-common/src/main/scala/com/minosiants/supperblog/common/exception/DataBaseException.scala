package com.minosiants.supperblog.common.exception

case class DataBaseException(error:String="",exception:String="",message:String="") extends RuntimeException