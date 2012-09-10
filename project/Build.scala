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
        "org.brickred" % "socialauth" % "2.3"
    )

    val main = PlayProject(appName, appVersion, appDependencies, mainLang = JAVA).settings (
        // Add your own project settings here
        // The Typesafe repository
        resolvers += "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/",

        // The Ocpsoft repository for PrettyTime
        resolvers += "ocpsoft repository" at "http://ocpsoft.org/repository/",
        
        // The Sonatype repository for socialauth
        resolvers += "sonatype-oss-public" at "http://oss.sonatype.org/content/groups/public/"
    )

}
