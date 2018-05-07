package com.github.bhop.twippy.domain.parser

import com.github.bhop.twippy.domain.Status
import com.github.bhop.twippy.domain.Status.{Deletion, Tweet}
import io.circe.parser.decode
import io.circe.generic.auto._
import cats.syntax.either._

private[domain] object StatusParser extends Parser[Status] {

  override def parse(json: String): Either[Parser.ParsingError, Status] =
    decode[Deletion](json).orElse {
      decode[Tweet](json)
    }.leftMap(error => Parser.ParsingError(error.getMessage, json))
}
