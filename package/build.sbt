enablePlugins(Example)

libraryDependencies += "org.scalatest" %%% "scalatest" % "3.0.6-SNAP2" % Test

publishArtifact := false

libraryDependencies ++= {
  import Ordering.Implicits._
  if (VersionNumber(scalaVersion.value).numbers >= Seq(2L, 13L)) {
    Nil
  } else {
    Seq(
      "org.scala-lang.plugins" %% "scala-continuations-library" % "1.0.3" % Optional,
      "org.scala-lang.modules" %% "scala-async" % "0.9.7" % Optional,
      "com.typesafe.akka" %% "akka-actor" % "2.5.14" % Optional,
      "com.twitter" %% "algebird-core" % "0.13.4" % Optional,
      "com.thoughtworks.binding" %% "binding" % "11.0.1" % Optional,
      "org.scala-lang.modules" %% "scala-parser-combinators" % "1.1.1" % Optional,
      "org.scalacheck" %% "scalacheck" % "1.14.0" % Optional,
      "com.thoughtworks.each" %% "each" % "3.3.1" % Optional,
      "com.lihaoyi" %% "sourcecode" % "0.1.4" % Optional,
      "io.monix" %% "monix" % "2.3.3" % Optional,
      "com.typesafe.akka" %% "akka-stream" % "2.5.14" % Optional,
      "com.typesafe.akka" %% "akka-http" % "10.1.3" % Optional
    )
  }
}

sourceGenerators in Test := {
  (sourceGenerators in Test).value.filterNot { sourceGenerator =>
    import Ordering.Implicits._
    VersionNumber(scalaVersion.value).numbers >= Seq(2L, 13L) &&
    sourceGenerator.info
      .get(taskDefinitionKey)
      .exists { scopedKey: ScopedKey[_] =>
        scopedKey.key == generateExample.key
      }
  }
}

libraryDependencies += "org.scalaz" %% "scalaz-concurrent" % "7.2.26"

import scala.meta._

exampleSuperTypes := exampleSuperTypes.value.filter {
  case ctor"_root_.org.scalatest.FreeSpec" =>
    false
  case _ =>
    true
}

exampleSuperTypes := ctor"_root_.org.scalatest.AsyncFreeSpec" +: exampleSuperTypes.value
exampleSuperTypes += ctor"_root_.org.scalatest.Inside"
exampleSuperTypes += ctor"_root_.com.thoughtworks.dsl.MockPingPongServer"

addCompilerPlugin("org.spire-math" %% "kind-projector" % "0.9.7")

scalacOptions ++= {
  import Ordering.Implicits._
  if (VersionNumber(scalaVersion.value).numbers >= Seq(2L, 13L)) {
    Seq("-Ymacro-annotations")
  } else {
    Nil
  }
}

libraryDependencies ++= {
  import Ordering.Implicits._
  if (VersionNumber(scalaVersion.value).numbers >= Seq(2L, 13L)) {
    Nil
  } else {
    Seq(compilerPlugin("org.scalamacros" % "paradise" % "2.1.1" cross CrossVersion.full))
  }
}
