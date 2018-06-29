package com.github.bhop.twippy.http

import com.github.bhop.twippy.http.HttpBackend.HttpRequest

private[twippy] trait HttpBackend[F[_]] {

  def send[R](request: HttpRequest): F[R]

  def release: F[Unit]
}

private[twippy] object HttpBackend {

  final case class QueryParam(key: String, value: String)

  final case class HttpUri(data: String) extends AnyVal

  final case class ConsumerToken(token: String, secret: String)

  final case class AccessToken(token: String, secret: String)

  final case class Auth(consumerToken: ConsumerToken, accessToken: AccessToken)

  sealed trait HttpRequest {
    def uri: HttpUri
    def queryParams: List[QueryParam]
    def auth: Auth
  }

  final case class Get(uri: HttpUri, queryParams: List[QueryParam], auth: Auth) extends HttpRequest
}