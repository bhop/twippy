package com.github.bhop.twippy.rest.clients

import cats.effect.{IO, Sync}
import com.github.bhop.twippy.core.entity.application.RateLimits
import com.github.bhop.twippy.core.entity.application.RateLimits.{RateLimitContext, Resources}
import com.github.bhop.twippy.core.http.HttpBackend
import com.github.bhop.twippy.core.TwitterUris._
import com.github.bhop.twippy.core.http.HttpBackend.{Get, QueryParam, HttpRequest}
import com.github.bhop.twippy.rest.TestHttpBackend
import com.github.bhop.twippy.core.entity.application.Resource.{Account, Geo}
import org.scalatest.{MustMatchers, WordSpec}
import cats.implicits._

class ApplicationClientSpec extends WordSpec with MustMatchers with TestHttpBackend {

  "Application Client" when {

    "rateLimits called with a non empty list" should {

      "return rate limits for selected resources" in {
        val client = new ApplicationClient[IO](httpBackend[IO]) {}
        client.rateLimits(Seq(Account, Geo)).unsafeRunSync() mustBe RateLimitsForSelectedResources
      }
    }

    "rateLimits called with an empty list" should {

      "return rate limits for all resources" in {
        val client = new ApplicationClient[IO](httpBackend[IO]) {}
        client.rateLimits().unsafeRunSync() mustBe RateLimitsForAll
      }
    }
  }

  def RateLimitsForAll: RateLimits =
    RateLimits(RateLimitContext("all"), resources = Resources())

  def RateLimitsForSelectedResources: RateLimits =
    RateLimitsForAll.copy(rate_limit_context = RateLimitContext("selected"))

  def httpBackend[F[_]: Sync]: HttpBackend[F] = testHttpBackend[F] {
    case HttpRequest(Get, `twitterRateLimitsUri`, Seq(), None) =>
      RateLimitsForAll.pure[F]
    case HttpRequest(Get, `twitterRateLimitsUri`, Seq(QueryParam("resources", "account,geo")), None) =>
      RateLimitsForSelectedResources.pure[F]
  }
}