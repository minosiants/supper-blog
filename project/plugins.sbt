// Comment to get more information during initialization
logLevel := Level.Warn

// The Typesafe repository 
resolvers += "Typesafe releases repository" at "http://repo.typesafe.com/typesafe/releases/"

//resolvers += "Typesafe snapshots repository" at "http://repo.typesafe.com/typesafe/snapshots/"

// Use the Play sbt plugin for Play projects

addSbtPlugin("play" % "sbt-plugin" % "2.0.1")

//addSbtPlugin("com.typesafe.sbteclipse" % "sbteclipse-plugin" % "2.1.0-RC1")