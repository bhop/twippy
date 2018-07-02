package com.github.bhop.twippy.backend.blaze

import cats.effect.Sync
import com.github.bhop.twippy.http.HttpBackend
import org.http4s.{Method, Query, Request, Uri}
import org.http4s.client.oauth1.{Consumer, Token}

import cats.implicits._

private[blaze] abstract class RequestExtractor[F[_]](implicit F: Sync[F]) {

  import HttpBackend._
  import RequestExtractor._

  def extractRequest(request: HttpRequest): F[Request[F]] =
    for {
      method  <- extractHttpMethod(request)
      uri     <- extractBaseUri(request.uri.path)
      query   <- extractQuery(request.uri.query)
    } yield Request[F](method, uri.copy(query = query))


  def extractConsumer(consumerToken: ConsumerToken): F[Consumer] =
    Consumer(consumerToken.token, consumerToken.secret).pure[F]

  def extractToken(accessToken: AccessToken): F[Token] =
    Token(accessToken.token, accessToken.secret).pure[F]

  private def extractHttpMethod(request: HttpRequest): F[Method] =
    request match {
      case _: Get => F.pure(Method.GET)
    }

  private def extractBaseUri(path: String): F[Uri] =
    Uri.fromString(path).fold(f => F.raiseError(UnableToParseGivenUri(f.details)), _.pure[F])

  private def extractQuery(queryParams: List[QueryParam]): F[Query] =
    queryParams.map(p => p.key -> p.value.some).foldLeft[Query](Query.empty)(_ :+ _).pure[F]
}

object RequestExtractor {

  final case class UnableToParseGivenUri(details: String) extends Exception(details)
}
