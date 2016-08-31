name := """temp1"""

version := "1.0"

scalaVersion := "2.11.7"

// Change this to another test framework if you prefer
libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "2.2.4" % "test",
  "org.scalikejdbc" %% "scalikejdbc"       % "2.4.2",
  "org.scalikejdbc" %% "scalikejdbc-test"   % "2.4.2"   % "test",
  "ch.qos.logback"  %  "logback-classic"   % "1.1.7",
  "org.postgresql" % "postgresql" % "9.4.1208.jre7",
  "joda-time" % "joda-time" % "2.9.4"
)

