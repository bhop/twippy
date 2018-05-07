import mill._
import mill.scalalib._

object twippy extends ScalaModule {

  def scalaVersion = "2.12.6"

  override def ivyDeps = Agg(
    ivy"co.fs2::fs2-core:0.10.1",
    ivy"org.http4s::http4s-blaze-client:0.18.10",
    ivy"io.circe::circe-parser:0.9.3",
    ivy"io.circe::circe-generic:0.9.3",
    ivy"ch.qos.logback:logback-classic:1.2.3"
  )

  override def scalacOptions = Seq(
    "-unchecked",
    "-deprecation",
    "-feature",
    "-language:existentials",
    "-language:higherKinds",
    "-language:implicitConversions",
    "-language:postfixOps",
    "-Ywarn-dead-code",
    "-Ywarn-infer-any",
    "-Ywarn-unused-import",
    "-Ypartial-unification",
    "-Xfatal-warnings",
    "-Xlint"
  )

  object test extends Tests {
    override def ivyDeps = Agg(
      ivy"org.scalatest::scalatest:3.0.5"
    )
    override def testFrameworks = Seq("org.scalatest.tools.Framework")
  }
}