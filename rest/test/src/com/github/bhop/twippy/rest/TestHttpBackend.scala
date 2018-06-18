package com.github.bhop.twippy.rest

import cats.effect.Sync
import com.github.bhop.twippy.core.http.HttpBackend
import com.github.bhop.twippy.core.http.HttpBackend.HttpRequest
import com.github.bhop.twippy.rest.TestHttpBackend._

trait TestHttpBackend {

  def testHttpBackend[F[_]: Sync](p: MatchRequest[F]): HttpBackend[F] =
    new StubHttpBackend[F] {
      override def send[R](request: HttpRequest): F[R] = p(request).asInstanceOf[F[R]]
    }
}

object TestHttpBackend {

  type MatchRequest[F[_]] = PartialFunction[HttpRequest, F[_]]

  class StubHttpBackend[F[_]](implicit F: Sync[F]) extends HttpBackend[F] {
    override def send[R](request: HttpRequest): F[R] = F.raiseError(new IllegalArgumentException)
    override def release: F[Unit] = F.unit
  }
}
