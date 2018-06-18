package com.github.bhop.twippy.rest.clients

import com.github.bhop.twippy.core.entity.application.{RateLimits, Resource}
import com.github.bhop.twippy.core.http.HttpBackend
import com.github.bhop.twippy.core.http.HttpBackend.{Get, QueryParam, HttpRequest}
import com.github.bhop.twippy.core.TwitterUris._

private[rest] abstract class ApplicationClient[F[_]](http: HttpBackend[F]) {

  /** Returns the current rate limits for methods belonging to the specified resource families.
    * For more information see:
    * <a href="https://developer.twitter.com/en/docs/developer-utilities/rate-limit-status/api-reference/get-application-rate_limit_status" target="_blank">
    *   Get app rate limit status</a>.
    *
    * @param resources : A sequence of resource families you want to know the current rate limit disposition for.
    *                  If no resources are specified, all the resources are considered.
    * @return : The current rate limits for methods belonging to the specified resource families.
    */
  def rateLimits(resources: Seq[Resource] = Seq.empty): F[RateLimits] =
    http.send[RateLimits](rateLimitsRequest(resources))

  private def rateLimitsRequest(resources: Seq[Resource]): HttpRequest =
    HttpRequest(Get, twitterRateLimitsUri, resourcesParam(resources))

  private def resourcesParam(resources: Seq[Resource]): Seq[QueryParam] =
    if (resources.isEmpty) Seq.empty[QueryParam]
    else Seq(QueryParam("resources", resources.map(_.value).mkString(",")))
}
