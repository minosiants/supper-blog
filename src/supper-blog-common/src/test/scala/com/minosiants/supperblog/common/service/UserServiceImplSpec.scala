package com.minosiants.supperblog.common.service

import com.minosiants.supperblog.common.Common
import org.specs2.mutable.Specification
import org.specs2.mutable.SpecificationWithJUnit
import org.junit.runner.RunWith
import org.specs2.runner.JUnitRunner

@RunWith(classOf[JUnitRunner])
class UserServiceImplSpec extends SpecificationWithJUnit with Common{
	 	
	"get user profile" in{
		val up=userService.getUserProfile("kaspar")
		up must not empty
		
	} 
}