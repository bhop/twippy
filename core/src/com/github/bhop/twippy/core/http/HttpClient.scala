package com.github.bhop.twippy.core.http

import io.circe.Decoder

private[twippy] trait HttpClient[F[_]] {

  def get[A: Decoder](uri: HttpClient.RequestUri): F[A]
}

private[twippy] object HttpClient {

  final case class QueryParam(key: String, value: String)

  final case class RequestUri(path: String, query: List[QueryParam])
}