package com.github.bhop.twippy.auth

import cats.effect.IO
import cats.syntax.option._
import cats.syntax.applicative._
import org.http4s.Request
import org.http4s.headers._
import org.http4s.util.CaseInsensitiveString
import org.scalatest.{Matchers, WordSpec}

class AuthSpec extends WordSpec with Matchers {

  "An OAuth 1 authenticator" should {

    "enrich request with an authorization header" in {
      val signed = for {
        req       <- Request[IO]().pure[IO]
        consumer  <- ConsumerToken("token", "secret").pure[IO]
        token     <- AccessToken("token", "secret").pure[IO]
        signed    <- Auth.oauthSign[IO](req)(consumer, token)
      } yield signed
      val header = signed.unsafeRunSync().headers.get(Authorization)
      header.map(_.credentials.authScheme) shouldBe CaseInsensitiveString("OAuth").some
    }
  }
}
