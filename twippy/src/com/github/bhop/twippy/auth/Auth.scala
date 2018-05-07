package com.github.bhop.twippy.auth

import cats.Monad
import cats.syntax.option._
import cats.syntax.functor._
import cats.syntax.flatMap._
import cats.syntax.applicative._
import org.http4s.{EntityDecoder, Request, Uri, UrlForm}
import org.http4s.client.oauth1.{Consumer, Token, signRequest}

object Auth {

  def oauthSign[F[_]: Monad](req: Request[F])(consumerToken: ConsumerToken, accessToken: AccessToken)
                            (implicit ed: EntityDecoder[F, UrlForm]): F[Request[F]] =
    for {
      consumer  <- Consumer(consumerToken.token, consumerToken.secret).pure[F]
      token     <- Token(accessToken.token, accessToken.secret).pure[F]
      signed    <- signRequest(req, consumer, none[Uri], none[String], token.some)
    } yield signed
}
