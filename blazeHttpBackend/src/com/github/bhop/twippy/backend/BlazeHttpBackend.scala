package com.github.bhop.twippy.backend

import cats.effect.Effect
import com.github.bhop.twippy.core.http.HttpBackend
import com.github.bhop.twippy.core.http.HttpBackend.HttpRequest
import org.http4s.client.Client
import org.http4s.client.blaze.{BlazeClientConfig, Http1Client}

import cats.implicits._

class BlazeHttpBackend[F[_]: Effect](client: Client[F]) extends HttpBackend[F] {

  override def send[R](request: HttpRequest): F[R] = ???

  override def release: F[Unit] = client.shutdown
}

object BlazeHttpBackend {

  def apply[F[_]: Effect](config: BlazeClientConfig): F[BlazeHttpBackend[F]] =
    Http1Client(config).map(new BlazeHttpBackend[F](_))

  def apply[F[_]: Effect]: F[BlazeHttpBackend[F]] =
    apply(BlazeClientConfig.defaultConfig)

  def fromClient[F[_]: Effect](client: Client[F]): F[BlazeHttpBackend[F]] =
    new BlazeHttpBackend[F](client).pure[F]
}