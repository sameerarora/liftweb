name := "lift-qa-html"

version := "1.0"

organization := "xebia"

crossScalaVersions := Seq("2.12.1")

scalaVersion := crossScalaVersions.value.head

Seq(webSettings :_*)

libraryDependencies ++={
  val liftVersion = "3.0.1"
  Seq(
    "net.liftweb" %% "lift-webkit" % liftVersion % "compile",
    "org.eclipse.jetty" % "jetty-webapp" % "8.1.7.v20120910" % "container, test",
    "org.eclipse.jetty.orbit" % "javax.servlet" % "3.0.0.v201112011016"
      % "container" artifacts Artifact("javax.servlet", "jar", "jar"),
    "ch.qos.logback"    % "logback-classic"     % "1.0.6",
    "net.liftmodules"   %% "lift-jquery-module_3.1" % "2.10",
    "net.liftmodules" %% "widgets_3.0" % "1.4.1",
    "com.h2database" % "h2" % "1.3.170",
    "net.liftweb" %% "lift-squeryl-record" % liftVersion,
    "org.specs2" % "specs2-core_2.12" % "3.9.5" % "test"
  )
}

port in container.Configuration := 8081