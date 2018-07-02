import mill._
import mill.scalalib._

object circe extends TwippyModule {
  override def moduleDeps = Seq(core)
  override def ivyDeps = Agg(
    ivy"io.circe::circe-parser:0.9.3",
    ivy"io.circe::circe-generic:0.9.3"
  )
}

object blaze extends TwippyModule {
  override def moduleDeps = Seq(core)
  override def ivyDeps = Agg(
    ivy"org.http4s::http4s-blaze-client:0.18.12"
  )
}

object core extends TwippyModule {
  override def ivyDeps = Agg(
    ivy"org.typelevel::cats-effect:1.0.0-RC2"
  )
}

object tests extends TwippyModule {
  override def moduleDeps = Seq(blaze, circe)
}

trait TwippyModule extends ScalaModule {

  def scalaVersion = "2.12.6"

  override def compileIvyDeps = Agg(ivy"org.spire-math::kind-projector:0.9.6")

  override def scalacPluginIvyDeps = Agg(ivy"org.spire-math::kind-projector:0.9.6")

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
    def testFrameworks = Seq("org.scalatest.tools.Framework")
  }
}