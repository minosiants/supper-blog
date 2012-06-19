package com.minosiants.supperblog.common

import org.junit.runner.RunWith
import org.specs2.mutable.SpecificationWithJUnit
import org.specs2.runner.JUnitRunner
import com.minosiants.supperblog.common.model.UserProfile

@RunWith(classOf[JUnitRunner])
class UserTokenSpec extends SpecificationWithJUnit {
	
	"get user " in{
	  val user=UserToken("a2FzcGFyMg==|2592000|LHWeA92rYJsIKiMmqorItfD1KW8=").user.get
	  println(user) 
	}
}