package com.github.bhop.twippy.rest.clients

import cats.data.NonEmptyList
import com.github.bhop.twippy.core.entity.application.{RateLimits, Resource}
import com.github.bhop.twippy.core.http.HttpClient

private[rest] trait ApplicationClient[F[_]] {

  protected val http: HttpClient[F]

  def rateLimits(resources: NonEmptyList[Resource]): F[RateLimits] =
    http.get[RateLimits](???)
}
