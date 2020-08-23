name := "SparkDataGenerator"
version := "0.1"
scalaVersion := "2.11.8"
organization := "org.thompson"

libraryDependencies += "org.apache.spark" %% "spark-core" % "2.2.1" % "provided"
libraryDependencies += "org.apache.spark" %% "spark-sql" % "2.2.1" % "provided"
libraryDependencies += "org.scalacheck" %% "scalacheck" % "1.14.0"

githubOwner := "ajaythompson"
githubRepository := "SparkRandomDataGenerator"

resolvers += "github repository" at "https://maven.pkg.github.com/ajaythompson/SparkRandomDataGenerator"