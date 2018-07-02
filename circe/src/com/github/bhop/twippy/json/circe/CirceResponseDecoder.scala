package com.github.bhop.twippy.json.circe

import cats.effect.Sync
import com.github.bhop.twippy.entity.application.RateLimits
import com.github.bhop.twippy.entity.help.Configuration
import com.github.bhop.twippy.http.ResponseDecoder
import com.github.bhop.twippy.http.ResponseDecoder.UnableToParseJson
import io.circe.Decoder
import io.circe.parser.decode
import io.circe.generic.auto._

private[circe] class CirceResponseDecoder[F[_]](implicit F: Sync[F]) extends ResponseDecoder[F] {

  override def decodeRateLimits(input: String): F[RateLimits] = decodeResponse[RateLimits](input)

  override def decodeConfiguration(input: String): F[Configuration] = decodeResponse[Configuration](input)

  private def decodeResponse[T: Decoder](input: String): F[T] =
    decode[T](input).fold(fa => F.raiseError(UnableToParseJson(fa.getMessage)), F.pure)
}

object CirceResponseDecoder {

  def apply[F[_]: Sync]: CirceResponseDecoder[F] =
    new CirceResponseDecoder[F]
}
