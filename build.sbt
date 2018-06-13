name := "example-source-spark"

version := "0.1"

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  "org.scalanlp" %% "breeze" % "0.12",

  "org.apache.spark" %% "spark-core" % "2.3.0" % "provided",
  "org.apache.spark" %% "spark-sql" % "2.3.0" % "provided",
  "org.apache.spark" %% "spark-streaming" % "2.3.0" % "provided",

  "org.scalatest" %% "scalatest" % "3.0.5" % "test"
)

resolvers ++= Seq(
  "Sonatype Releases" at "https://oss.sonatype.org/content/repositories/releases/"
)


javaOptions in Test ++= Seq("-Xmx14G -Xms4G -Xss2M -XX:+UseConcMarkSweepGC -XX:+CMSClassUnloadingEnabled -XX:MaxPermSize=2G")

test in assembly := {}
