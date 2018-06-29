package com.github.bhop.twippy.backend.blaze

import cats.effect.Sync
import com.github.bhop.twippy.http.HttpBackend
import org.http4s.{Method, Query, Request, Uri}
import org.http4s.client.Client
import cats.implicits._
import com.github.bhop.twippy.backend.blaze.BlazeHttpBackend.UnableToParseHttpUri
import com.github.bhop.twippy.http.HttpBackend.Get
import org.http4s.client.oauth1.{Consumer, Token, _}

class BlazeHttpBackend[F[_]](client: Client[F])(implicit F: Sync[F]) extends HttpBackend[F] {

  override def send[R](request: HttpBackend.HttpRequest): F[R] =
    for {
      consumer      <- extractConsumer(request.auth.consumerToken)
      token         <- extractToken(request.auth.accessToken)
      request       <- extractRequest(request)
      signedRequest <- sign(request, consumer, token)
      response      <- client.expect[R](signedRequest)
    } yield response

  private def sign(request: Request[F], consumer: Consumer, token: Token): F[Request[F]] =
    signRequest(request, consumer, none[Uri], none[String], token.some)

  private def extractRequest(request: HttpBackend.HttpRequest): F[Request[F]] =
    for {
      method <- extractHttpMethod(request)
      query  <- extractQueryParams(request.queryParams)
      uri    <- extractUri(request.uri)
    } yield Request[F](method, uri.copy(query = query))

  private def extractHttpMethod(request: HttpBackend.HttpRequest): F[Method] =
    request match {
      case _: Get => F.pure(Method.GET)
    }

  private def extractUri(uri: HttpBackend.HttpUri): F[Uri] =
    Uri.fromString(uri.data).fold(e => F.raiseError(UnableToParseHttpUri(e.details)), _.pure[F])

  private def extractQueryParams(queryParams: List[HttpBackend.QueryParam]): F[Query] =
    queryParams.foldLeft[Query](Query.empty) { case (query, queryParam) =>
      query :+ (queryParam.key, queryParam.value.some) // fixme: does not compile?!?!?!
    }.pure[F]

  private def extractConsumer(consumerToken: HttpBackend.ConsumerToken): F[Consumer] =
    Consumer(consumerToken.token, consumerToken.secret).pure[F]

  private def extractToken(accessToken: HttpBackend.AccessToken): F[Token] =
    Token(accessToken.token, accessToken.secret).pure[F]

  override def release: F[Unit] = client.shutdown
}

object BlazeHttpBackend {

  final case class UnableToParseHttpUri(details: String) extends Exception(details)
}
