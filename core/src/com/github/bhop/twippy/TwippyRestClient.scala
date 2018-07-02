package com.github.bhop.twippy

import cats.effect.Effect
import com.github.bhop.twippy.http.{HttpBackend, ResponseDecoder}
import com.github.bhop.twippy.http.HttpBackend.Auth
import com.github.bhop.twippy.rest.developer.{ApplicationRest, ConfigurationRest}

class TwippyRestClient[F[_]: Effect] private (val auth: Auth, val backend: HttpBackend[F], val decoder: ResponseDecoder[F])
  extends ApplicationRest[F] with ConfigurationRest[F]

object TwippyRestClient {

  def apply[F[_]: Effect](auth: Auth)(backend: HttpBackend[F], decoder: ResponseDecoder[F]): TwippyRestClient[F] =
    new TwippyRestClient[F](auth, backend, decoder)
}