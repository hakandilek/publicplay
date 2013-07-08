// Comment to get more information during initialization
logLevel := Level.Warn

// The Typesafe repository
resolvers += "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/"

// Jacoco for code coverage
libraryDependencies ++= Seq(
  "org.jacoco" % "org.jacoco.core" % "0.5.9.201207300726" artifacts(Artifact("org.jacoco.core", "jar", "jar")),
  "org.jacoco" % "org.jacoco.report" % "0.5.9.201207300726" artifacts(Artifact("org.jacoco.report", "jar", "jar")))

// Jacoco for code coverage
addSbtPlugin("de.johoop" % "jacoco4sbt" % "1.2.4")

// Use the Play sbt plugin for Play projects
addSbtPlugin("play" % "sbt-plugin" % "2.1.1")
