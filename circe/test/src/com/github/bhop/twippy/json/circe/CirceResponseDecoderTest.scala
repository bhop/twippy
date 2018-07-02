package com.github.bhop.twippy.json.circe

import cats.effect.{IO, Sync}
import com.github.bhop.twippy.entity.application.RateLimits
import com.github.bhop.twippy.entity.application.RateLimits.{RateLimit, RateLimitContext, Resources}
import org.scalatest.{MustMatchers, WordSpec}
import cats.implicits._

import scala.io.Source

class CirceResponseDecoderTest extends WordSpec with MustMatchers {

  "CirceResponseDecoder" when {

    "decodeRateLimits" should {

      "decode json to a case class" in {
        val program = for {
          input   <- json[IO]("rateLimits")
          decoded <- CirceResponseDecoder[IO].decodeRateLimits(input)
        } yield decoded

        val expectedRateLimits =
          RateLimits(
            rate_limit_context = RateLimitContext(access_token = "3022759941-NieB2h92ZeqDLPe8NUKhbyGGoJrgekrBWBbKza2"),
            resources = Resources(
              account = Map(
                "/account/login_verification_enrollment" -> RateLimit(15, 15, 1530465845),
                "/account/update_profile" -> RateLimit(15, 15, 1530465845),
                "/account/verify_credentials" -> RateLimit(75, 75, 1530465845),
                "/account/settings" -> RateLimit(15, 15, 1530465845)
              ).some,
              search = Map(
                "/search/tweets" -> RateLimit(180, 180, 1530465845)
              ).some
            )
          )

        program.unsafeRunSync() mustBe expectedRateLimits
      }
    }
  }

  private def json[F[_]](name: String)(implicit F: Sync[F]): F[String] =
    F.delay {
      Source.fromResource("json/" + name + ".json").mkString
    }
}
