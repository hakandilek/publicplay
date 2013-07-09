import sbt._
import Keys._
import play.Project._
import de.johoop.jacoco4sbt._
import JacocoPlugin._

object ApplicationBuild extends Build {

    val appName         = "PublicPlay"
    val appVersion      = "0.1.1-SNAPSHOT"

    // Jacoco for code coverage
    lazy val s = Defaults.defaultSettings ++ Seq(jacoco.settings:_*)
    
    val appDependencies = Seq(
       	
       	javaCore, javaJdbc, javaEbean,
       	
        // Add your project dependencies here,
        //prettytime library
        "org.ocpsoft.prettytime" % "prettytime" % "2.1.2.Final",
        // facebook authentication library
        "com.feth" %% "play-authenticate" % "0.2.5-SNAPSHOT",
        //appfog/cloudfoundry integration
        "org.cloudfoundry" % "auto-reconfiguration" % "0.6.6" excludeAll(ExclusionRule(organization = "org.slf4j")),
        "com.restfb" % "restfb" % "1.6.11",
        "joda-time" % "joda-time"% "2.1",
        "be.objectify" %% "deadbolt-java" % "2.1-RC2",
        "com.amazonaws" % "aws-java-sdk" % "1.3.11",
        "postgresql" % "postgresql" % "9.1-901.jdbc4",
        "com.google.inject" % "guice" % "3.0",
        "play2-cache" % "play2-cache_2.10" % "0.6.0-SNAPSHOT",
        "play2-crud" % "play2-crud_2.10" % "0.6.0-SNAPSHOT",
        "com.pickleproject" % "pickle-core" % "0.6-SNAPSHOT",
        "com.pickleproject" % "pickle-shopping" % "0.6-SNAPSHOT",
        "com.typesafe" %% "play-plugins-mailer" % "2.1.0"
    )

    val main = play.Project(appName, appVersion, appDependencies, settings = s).settings (
        // Add your own project settings here
        // The Typesafe repository
        resolvers += "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/",
        
        //Maven central repo
        resolvers += "Maven repository" at "http://repo1.maven.org/maven/", 

        // repositories for play-authenticate
		resolvers += Resolver.url("play-easymail (release)", url("http://joscha.github.com/play-easymail/repo/releases/"))(Resolver.ivyStylePatterns),
		resolvers += Resolver.url("play-easymail (snapshot)", url("http://joscha.github.com/play-easymail/repo/snapshots/"))(Resolver.ivyStylePatterns),
		resolvers += Resolver.url("play-authenticate (release)", url("http://joscha.github.com/play-authenticate/repo/releases/"))(Resolver.ivyStylePatterns),
		resolvers += Resolver.url("play-authenticate (snapshot)", url("http://joscha.github.com/play-authenticate/repo/snapshots/"))(Resolver.ivyStylePatterns),
        
        // The Spring repository for auto-reconfiguration (appfog)
        resolvers += "Spring repository" at "http://maven.springframework.org/milestone/",
        
        //maven repository
        resolvers += "release repository" at  "http://hakandilek.github.com/maven-repo/releases/",
        resolvers += "snapshot repository" at "http://hakandilek.github.com/maven-repo/snapshots/",
        
        // Objectify Repository for Deadbolt
        resolvers += Resolver.url("Objectify Play Repository", url("http://schaloner.github.com/releases/"))(Resolver.ivyStylePatterns),
        resolvers += Resolver.url("Objectify Play Repository - snapshots", url("http://schaloner.github.com/snapshots/"))(Resolver.ivyStylePatterns),
        
        // The Sonatype repository for PrettyTime
        resolvers += "sonatype-oss-public" at "http://oss.sonatype.org/content/groups/public/",
        
        // Jacoco configuration for code coverage
    	parallelExecution     in jacoco.Config := false,
    	jacoco.reportFormats  in jacoco.Config := Seq(XMLReport("utf-8"), HTMLReport("utf-8")),
    	jacoco.excludes       in jacoco.Config := Seq(
    	    "*Routes*",
    	    "controllers*ref*", 
    	    "controllers*javascript*", 
    	    "controllers*routes", 
    	    "controllers*routes.*",
    	    "views*html*",
    	    "views*html*admin*",
    	    "views*html*errors*",
    	    "views*html*page*",
    	    "views*html*partials*",
    	    "views*html*template*"
	    )
        
        //ignore checksum check
        //checksums := Nil
    )

}
