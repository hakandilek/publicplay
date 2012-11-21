import sbt._
import Keys._
import PlayProject._

object ApplicationBuild extends Build {

    val appName         = "PublicPlay"
    val appVersion      = "0.1.0-SNAPSHOT"

    val appDependencies = Seq(
        // Add your project dependencies here,
        //prettytime library
        "org.ocpsoft.prettytime" % "prettytime" % "2.0.0-SNAPSHOT",
        //socialauth library
        "org.brickred" % "socialauth" % "3.0",
        "com.restfb" % "restfb" % "1.6.11",
        "play2-cache" % "play2-cache_2.9.1" % "0.3.0-SNAPSHOT",
        "joda-time" % "joda-time"% "2.1",
        "be.objectify" %% "deadbolt-2" % "1.1.2",
        "com.amazonaws" % "aws-java-sdk" % "1.3.11"
    )

    val main = PlayProject(appName, appVersion, appDependencies, mainLang = JAVA).settings (
        // Add your own project settings here
        // The Typesafe repository
        resolvers += "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/",

        // The Ocpsoft repository for PrettyTime
        resolvers += "ocpsoft repository" at "http://ocpsoft.org/repository/",
        
        // The Sonatype repository for socialauth
        resolvers += "sonatype-oss-public" at "http://oss.sonatype.org/content/groups/public/",
        
        //play2-cache repository
        resolvers += Resolver.url("play2-cache release repository", url("http://hakandilek.github.com/play2-cache/releases/"))(Resolver.ivyStylePatterns),
        resolvers += Resolver.url("play2-cache snapshot repository", url("http://hakandilek.github.com/play2-cache/snapshots/"))(Resolver.ivyStylePatterns),
        
        // Objectify Repository for Deadbolt
        resolvers += Resolver.url("Objectify Play Repository", url("http://schaloner.github.com/releases/"))(Resolver.ivyStylePatterns)
        
        //ignore checksum check
        //checksums := Nil
    )

}
