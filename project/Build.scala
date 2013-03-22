import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

    val appName         = "PublicPlay"
    val appVersion      = "0.1.1-SNAPSHOT"

    val appDependencies = Seq(
       	
       	javaCore, javaJdbc, javaEbean,
       	
        // Add your project dependencies here,
        //prettytime library
        "org.ocpsoft.prettytime" % "prettytime" % "2.0.0-SNAPSHOT",
        //socialauth library
        "org.brickred" % "socialauth" % "4.0",
        //appfog/cloudfoundry integration
        "org.cloudfoundry" % "auto-reconfiguration" % "0.6.6" excludeAll(ExclusionRule(organization = "org.slf4j")),
        "com.restfb" % "restfb" % "1.6.11",
        "joda-time" % "joda-time"% "2.1",
        "be.objectify" %% "deadbolt-java" % "2.1-SNAPSHOT",
        "com.amazonaws" % "aws-java-sdk" % "1.3.11",
        "com.google.inject" % "guice" % "3.0",
        "play2-cache" % "play2-cache_2.10" % "0.4.0-SNAPSHOT",
        "play2-crud" % "play2-crud_2.10" % "0.3.0-SNAPSHOT",
        "com.pickleproject" % "pickle-core" % "0.6-SNAPSHOT",
        "com.pickleproject" % "pickle-shopping" % "0.6-SNAPSHOT",
        "com.typesafe" %% "play-plugins-mailer" % "2.1.0"
    )

    val main = play.Project(appName, appVersion, appDependencies).settings (
        // Add your own project settings here
        // The Typesafe repository
        resolvers += "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/",
        
        //Maven central repo
        resolvers += "Maven repository" at "http://repo1.maven.org/maven/", 

        // The Ocpsoft repository for PrettyTime
        resolvers += "ocpsoft repository" at "http://ocpsoft.org/repository/",
        
        // The Sonatype repository for socialauth
        resolvers += "sonatype-oss-public" at "http://oss.sonatype.org/content/groups/public/",
        
        // The Spring repository for auto-reconfiguration (appfog)
        resolvers += "Spring repository" at "http://maven.springframework.org/milestone/",
        
        //maven repository
        resolvers += "release repository" at  "http://hakandilek.github.com/maven-repo/releases/",
        resolvers += "snapshot repository" at "http://hakandilek.github.com/maven-repo/snapshots/",
        
        // Objectify Repository for Deadbolt
        resolvers += Resolver.url("Objectify Play Repository", url("http://schaloner.github.com/releases/"))(Resolver.ivyStylePatterns),
        resolvers += Resolver.url("Objectify Play Repository - snapshots", url("http://schaloner.github.com/snapshots/"))(Resolver.ivyStylePatterns)
        
        //ignore checksum check
        //checksums := Nil
    )

}
