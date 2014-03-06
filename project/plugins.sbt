// Comment to get more information during initialization
logLevel := Level.Warn

// The Typesafe repository
resolvers += "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/"

// Jacoco for code coverage
libraryDependencies ++= Seq(
  "org.jacoco" % "org.jacoco.core" % "0.6.4.201312101107" artifacts(Artifact("org.jacoco.core", "jar", "jar")),
  "org.jacoco" % "org.jacoco.report" % "0.6.4.201312101107" artifacts(Artifact("org.jacoco.report", "jar", "jar")))

// Jacoco for code coverage
addSbtPlugin("de.johoop" % "jacoco4sbt" % "2.1.4")

// dependency graph
addSbtPlugin("net.virtual-void" % "sbt-dependency-graph" % "0.7.4")

// Use the Play sbt plugin for Play projects
addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.2.2")