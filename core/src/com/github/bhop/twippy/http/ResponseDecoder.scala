package com.github.bhop.twippy.http

import com.github.bhop.twippy.entity.application.RateLimits
import com.github.bhop.twippy.entity.help.Configuration

trait ResponseDecoder[F[_]] {

  def decodeRateLimits(input: String): F[RateLimits]

  def decodeConfiguration(input: String): F[Configuration]
}

object ResponseDecoder {

  final case class UnableToParseJson(details: String) extends Exception(details)
}
