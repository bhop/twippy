package com.github.bhop.twippy.rest

import com.github.bhop.twippy.TwitterUris
import com.github.bhop.twippy.entity.application.{RateLimits, Resource}
import com.github.bhop.twippy.http.HttpBackend
import com.github.bhop.twippy.http.HttpBackend.{Get, HttpRequest, QueryParam, HttpUri}

import cats.implicits._

private[twippy] abstract class ApplicationRest[F[_]](http: HttpBackend[F], auth: HttpBackend.Auth) {

  /** Returns the current rate limits for methods belonging to the specified resource families.
    * For more information see:
    * <a href="https://developer.twitter.com/en/docs/developer-utilities/rate-limit-status/api-reference/get-application-rate_limit_status" target="_blank">
    *   Get app rate limit status</a>.
    *
    * @param resources : A sequence of resource families you want to know the current rate limit disposition for.
    *                  If no resources are specified, all the resources are considered.
    * @return : The current rate limits for methods belonging to the specified resource families.
    */
  def rateLimits(resources: List[Resource] = List.empty): F[RateLimits] =
    http.send[RateLimits](rateLimitsRequest(resources))

  private def rateLimitsRequest(resources: List[Resource]): HttpRequest =
    Get(HttpUri(TwitterUris.twitterRateLimitsUri), resourcesParam(resources), auth)

  private def resourcesParam(resources: List[Resource]): List[QueryParam] =
    if (resources.isEmpty) List.empty[QueryParam]
    else QueryParam("resources", resources.map(_.value).mkString(",")).pure[List]
}
