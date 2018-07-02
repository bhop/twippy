package com.github.bhop.twippy.rest.developer

import cats.implicits._
import com.github.bhop.twippy.TwitterUris
import com.github.bhop.twippy.entity.application.{RateLimits, Resource}
import com.github.bhop.twippy.http.HttpBackend._
import com.github.bhop.twippy.http.{HttpBackend, ResponseDecoder}

private[twippy] trait ApplicationRest[F[_]] {

  def auth: Auth
  def backend: HttpBackend[F]
  def decoder: ResponseDecoder[F]

  /** Returns the current rate limits for methods belonging to the specified resource families.
    *
    * For more information see:
    * <a href="https://developer.twitter.com/en/docs/developer-utilities/rate-limit-status/api-reference/get-application-rate_limit_status" target="_blank">
    *   Get app rate limit status</a>.
    *
    * @param resources A sequence of resource families you want to know the current rate limit disposition for.
    *                  If no resources are specified, all the resources are considered.
    * @return The current rate limits for methods belonging to the specified resource families.
    */
  def rateLimits(resources: List[Resource] = List.empty): F[RateLimits] =
    backend.send[RateLimits](rateLimitsRequest(resources))(decoder.decodeRateLimits)

  private def rateLimitsRequest(resources: List[Resource]): HttpRequest =
    Get(HttpUri(TwitterUris.twitterRateLimitsUri, resourcesParam(resources)), auth)

  private def resourcesParam(resources: List[Resource]): List[QueryParam] =
    if (resources.isEmpty) List.empty[QueryParam]
    else QueryParam("resources", resources.map(_.value).mkString(",")).pure[List]
}
