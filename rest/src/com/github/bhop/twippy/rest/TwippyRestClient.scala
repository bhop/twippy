package com.github.bhop.twippy.rest

import cats.effect.Effect
import com.github.bhop.twippy.core.http.HttpBackend
import com.github.bhop.twippy.rest.clients.ApplicationClient

class TwippyRestClient[F[_]: Effect] private (http: HttpBackend[F])
  extends ApplicationClient[F](http)

object TwippyRestClient {

  def apply[F[_]: Effect](implicit http: HttpBackend[F]): TwippyRestClient[F] =
    new TwippyRestClient[F](http)
}
