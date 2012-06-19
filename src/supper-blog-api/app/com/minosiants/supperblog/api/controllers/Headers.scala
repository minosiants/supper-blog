package com.minosiants.supperblog.api.controllers

object Headers {
	val ACCESS_CONTROL=List(("Access-Control-Allow-Origin","http://supper-blog.com:9000"),
							("Access-Control-Allow-Methods","GET, POST, PUT, DELETE, OPTIONS"),
							("Access-Control-Allow-Headers","X-CSRF-Token, X-Requested-With, Accept, Accept-Version, Content-Length, Content-MD5, Content-Type, Date, X-Api-Version"),							
							("Access-Control-Allow-Credentials","true")							
							)
}