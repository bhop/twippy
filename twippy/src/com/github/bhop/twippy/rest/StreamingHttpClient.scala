package com.github.bhop.twippy.rest

import cats.effect.Effect
import cats.syntax.applicative._
import fs2.{Stream, text}
import org.http4s.{Request, Response}
import org.http4s.client.blaze.Http1Client
import com.github.bhop.twippy.Logging
import com.github.bhop.twippy.domain.parser.Parser

private[twippy] trait StreamingHttpClient extends Logging {

  def stream[F[_]: Effect, T](request: Request[F])(implicit P: Parser[T]): Stream[F, T] =
    Http1Client.stream[F]().flatMap { client =>
      client.streaming(request)(decode(_)).flatMap(parse(_))
    }

  private def decode[F[_]: Effect](response: Response[F]): Stream[F, String] =
    response.body.chunks.through(text.utf8DecodeC).through(text.lines)

  private def parse[F[_]: Effect, T](json: String)(implicit P: Parser[T]): Stream[F, T] =
    P.parse(json).fold(empty(_), value => Stream.eval(value.pure[F]))

  private def empty[F[_]: Effect, T](error: Parser.ParsingError): Stream[F, T] =
    Stream.eval(warn[F](s"Could not parse json. Error: '${error.reason}'. Incoming json: '${error.json}'"))
      .flatMap(_ => Stream.empty)
}