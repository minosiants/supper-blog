import sbt._
import Keys._
import PlayProject._

object SupperBlogBuild extends Build {

    
    val org             = "com.minosiants.supperblog"
    
    val appDependencies = Seq(
    	"org.mongodb" % "casbah_2.9.1" % "2.3.0-RC1",
    	"commons-codec" % "commons-codec" % "1.6"
    	
     )

    lazy val supperBlogApiPlayProject = PlayProject(
        "supper-blog-api", 
        "1.0", 
        appDependencies,
        path=file("src/supper-blog-api"), 
        mainLang = SCALA
        ).settings(
      // Add your own project settings here      
        ).dependsOn(supperBlogCommonProject)     

    lazy val supperBlogClientPlayProject = PlayProject(
        "supper-blog-client", 
        "1.0",
        appDependencies,
        path=file("src/supper-blog-client"), 
        mainLang = SCALA
        ).settings(
      // Add your own project settings here      
        ).dependsOn(supperBlogCommonProject)
     

     lazy val supperBlogCommonProject = Project(
        "supper-blog-common",
        file("src/supper-blog-common"),     
        settings = Project.defaultSettings ++ Seq(
            name := "supper-blog-common",
            organization := org,
            version := "0.1",
      libraryDependencies ++= Seq(
        
            "org.mongodb" % "casbah_2.9.1" % "2.3.0-RC1",
            "commons-codec" % "commons-codec" % "1.6",
            "commons-lang" % "commons-lang" % "2.6"
        )
      // add other settings here
    )
    
  )

}
