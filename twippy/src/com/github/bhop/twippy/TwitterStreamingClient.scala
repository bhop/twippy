package com.github.bhop.twippy

import cats.effect.Effect
import cats.syntax.applicative._
import fs2.Stream
import com.github.bhop.twippy.auth.{AccessToken, Auth, ConsumerToken}
import com.github.bhop.twippy.domain.Status
import com.github.bhop.twippy.rest.StreamingHttpClient
import org.http4s.{Request, Uri}
import org.slf4j.LoggerFactory

class TwitterStreamingClient private (consumer: ConsumerToken, token: AccessToken) extends StreamingHttpClient
  with Logging {

  private val statusesUri: Uri = Configuration.twitterStreamUri / "statuses" / "sample.json"

  def sampleStatuses[F[_]: Effect]: Stream[F, Status] =
    for {
      request    <- Stream.eval(Request[F](uri = statusesUri).pure[F])
      authorized <- Stream.eval(Auth.oauthSign(request)(consumer, token))
      _          <- Stream.eval(info[F]("Executing request: " + authorized))
      samples    <- stream[F, Status](request = authorized)
    } yield samples

  override private[twippy] val logger = LoggerFactory.getLogger(this.getClass)
}

object TwitterStreamingClient {

  def apply(consumer: ConsumerToken, token: AccessToken): TwitterStreamingClient =
    new TwitterStreamingClient(consumer, token)
}
