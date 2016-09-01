name := """isharesetfdatadownload"""

version := "1.0"

scalaVersion := "2.11.8"

// Change this to another test framework if you prefer
libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "2.2.4" % "test",
  "org.scalamock" %% "scalamock-scalatest-support" % "3.2.2" % "test",
  "org.scalikejdbc" %% "scalikejdbc"       % "2.4.2",
  "org.scalikejdbc" %% "scalikejdbc-test"   % "2.4.2"   % "test",
  "ch.qos.logback"  %  "logback-classic"   % "1.1.7",
  "org.postgresql" % "postgresql" % "9.4.1208.jre7",
  "joda-time" % "joda-time" % "2.9.4",
  "com.typesafe" % "config" % "1.3.0",
  "org.flywaydb" % "flyway-core" % "4.0.3"
)

flywayUrl := sys.props.getOrElse("SECURITIESDATA_URL", default = "jdbc:postgresql://localhost:5432/securitiesdata")
flywayUser := sys.props.getOrElse("SECURITIESDATA_USER", default = "postgres")
flywayPassword := sys.props.getOrElse("SECURITIESDATA_PASSWORD", default = "postgres")

coverageEnabled in Test:= true