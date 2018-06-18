package com.github.bhop.twippy.core.http

object Auth {

  final case class ConsumerToken(token: String, secret: String)

  final case class AccessToken(token: String, secret: String)

  final case class OAuth1(accessToken: AccessToken, consumerToken: ConsumerToken)
}
