package com.github.bhop.twippy.domain.parser

private[twippy] trait Parser[T] {

  def parse(json: String): Either[Parser.ParsingError, T]
}

private[twippy] object Parser {
  case class ParsingError(reason: String, json: String)
}
