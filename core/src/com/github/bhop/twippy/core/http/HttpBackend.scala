package com.github.bhop.twippy.core.http

import com.github.bhop.twippy.core.http.Auth.OAuth1
import com.github.bhop.twippy.core.http.HttpBackend.HttpRequest

private[twippy] trait HttpBackend[F[_]] {

  def send[R](request: HttpRequest): F[R]

  def release: F[Unit]
}

private[twippy] object HttpBackend {

  final case class QueryParam(key: String, value: String)

  final case class HttpRequest(
                               method: HttpMethod,
                               uri: String,
                               queryParams: Seq[QueryParam],
                               oAuth1: Option[OAuth1] = None)

  sealed trait HttpMethod
  final case object Get extends HttpMethod
}