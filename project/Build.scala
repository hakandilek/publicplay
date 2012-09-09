import sbt._
import Keys._
import PlayProject._

object ApplicationBuild extends Build {

    val appName         = "PublicPlay"
    val appVersion      = "0.1.0-SNAPSHOT"

    val appDependencies = Seq(
        // Add your project dependencies here,
        //add prettytime library
        "org.ocpsoft.prettytime" % "prettytime" % "2.0.0-SNAPSHOT"
    )

	//securesocial dependencies
    val securesocialDependencies = Seq(
    	"com.typesafe" %% "play-plugins-util" % "2.0.1",
    	"org.mindrot" % "jbcrypt" % "0.3m"
    )
 
    val secureSocial = PlayProject(
    	"securesocial", appVersion, securesocialDependencies, mainLang = SCALA, path = file("modules/securesocial")
    ).settings(
      resolvers ++= Seq(
        "jBCrypt Repository" at "http://repo1.maven.org/maven2/org/",
        "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"
      )
    )

/*
    val secureSocial = PlayProject(
		//securesocial authentication library
	  	appName + "-securesocial", appVersion, mainLang = SCALA, path = file("modules/securesocial")
	)
*/

    val main = PlayProject(appName, appVersion, appDependencies, mainLang = JAVA).settings (
        // Add your own project settings here
        // The Typesafe repository
        resolvers += "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/",

		    // The Ocpsoft repository for PrettyTime
        resolvers += "ocpsoft repository" at "http://ocpsoft.org/repository/"
    ).dependsOn(secureSocial).aggregate(secureSocial)

}
