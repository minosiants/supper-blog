package com.minosiants.supperblog.common.model

import java.util.Date

object SortingOrder extends Enumeration{
	type SortingOrder=Value
	val ASC=Value(1)
	val DESC=Value(-1)
}
import SortingOrder._
case class QueryParams(since:Date=new Date(),limit:Int=20,orederBy:String="created",order:SortingOrder=DESC)
