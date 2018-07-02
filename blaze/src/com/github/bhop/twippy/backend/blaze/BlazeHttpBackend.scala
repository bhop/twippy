package com.github.bhop.twippy.backend.blaze

import cats.effect.Effect
import com.github.bhop.twippy.http.HttpBackend
import org.http4s.{Request, Uri}
import org.http4s.client.Client
import org.http4s.client.blaze.{BlazeClientConfig, Http1Client}
import org.http4s.client.oauth1._

import cats.implicits._

class BlazeHttpBackend[F[_]] private (client: Client[F])(implicit F: Effect[F])
  extends RequestExtractor[F] with HttpBackend[F] {

  override def send[R](request: HttpBackend.HttpRequest)(decode: String => F[R]): F[R] =
    for {
      consumer      <- extractConsumer(request.auth.consumerToken)
      token         <- extractToken(request.auth.accessToken)
      request       <- extractRequest(request)
      signedRequest <- sign(request, consumer, token)
      rawResponse   <- client.expect[String](signedRequest)
      response      <- decode(rawResponse)
    } yield response

  private def sign(request: Request[F], consumer: Consumer, token: Token): F[Request[F]] =
    signRequest(request, consumer, none[Uri], none[String], token.some)

  override def release: F[Unit] = client.shutdown
}

object BlazeHttpBackend {

  def apply[F[_]: Effect](config: BlazeClientConfig): F[BlazeHttpBackend[F]] =
    makeClient[F](config).flatMap(c => fromClient(c))

  def apply[F[_]: Effect]: F[BlazeHttpBackend[F]] =
    makeClient[F](BlazeClientConfig.defaultConfig).flatMap(c => fromClient(c))

  private[blaze] def fromClient[F[_]: Effect](client: Client[F]): F[BlazeHttpBackend[F]] =
    new BlazeHttpBackend[F](client).pure[F]

  private[blaze] def makeClient[F[_]: Effect](config: BlazeClientConfig): F[Client[F]] =
    Http1Client[F](config)
}