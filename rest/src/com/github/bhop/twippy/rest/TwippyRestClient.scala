package com.github.bhop.twippy.rest

import cats.effect.Effect
import com.github.bhop.twippy.core.http.Auth.{AccessToken, ConsumerToken}
import com.github.bhop.twippy.core.http.HttpClient
import com.github.bhop.twippy.rest.clients.{AccountClient, ApplicationClient}

class TwippyRestClient[F[_]: Effect] private (consumerToken: ConsumerToken, accessToken: AccessToken)
  extends AccountClient[F] with ApplicationClient[F] {

  protected val http: HttpClient[F] = ???
}
