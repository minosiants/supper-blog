package com.minosiants.supperblog.util

import org.specs2.mutable.SpecificationWithJUnit
import org.specs2.mutable.Specification
import com.minosiants.supperblog.util.Util._

class UtilSpec extends SpecificationWithJUnit{
	
  "find hashtags" in {
    val result=parseHashtags("this is #content with some #tags")
    //result must not beEmpty 
    result must have size 2 
    
  }
}