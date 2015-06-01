import sbt._

object Dependencies {
  val testDeps = Seq(
    "org.scalatest" %% "scalatest" % "2.2.1" % "test"
  )

  val peg3 = testDeps ++ Seq()
}
