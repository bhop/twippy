import mill._
import mill.scalalib._

object rest extends TwippyModule {
  override def moduleDeps = Seq(core)
}

object stream extends TwippyModule {
  override def moduleDeps = Seq(core)
}

object core extends TwippyModule {
  def ivyDeps = Agg(
    ivy"io.circe::circe-core:0.9.3"
  )
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