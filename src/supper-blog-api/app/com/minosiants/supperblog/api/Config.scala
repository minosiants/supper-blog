package com.minosiants.supperblog.api
import play.api.Play.current
import play.api.Configuration

object Config {
	lazy private val config = play.api.Play.configuration
	lazy val DB_HOST:String="127.0.0.1"//config.getString("db1.default.host").getOrElse("localhost")
	lazy val DB_PORT=27017//config.getInt("db1.default.port").getOrElse(27017)
	lazy val DB_NAME="supper-blog"//config.getString("db1.default.name").getOrElse("supper-blog")
	lazy val APP_SECRET="Ovt3`sypZ@HC=PD690@l63DTN]9^WTMovbdvI5:y/BshOh^Ukxi:SEMkA6`ccm@n"   
	
}