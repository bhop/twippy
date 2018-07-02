package com.github.bhop.twippy.tests

import cats.Show
import cats.effect.{ExitCode, IO, IOApp}
import com.github.bhop.twippy.json.circe.CirceResponseDecoder
import com.github.bhop.twippy.TwippyRestClient
import com.github.bhop.twippy.backend.blaze.BlazeHttpBackend
import com.github.bhop.twippy.entity.application.Resource
import com.github.bhop.twippy.http.HttpBackend._

import cats.implicits._

object RestTest extends IOApp {

  override def run(args: List[String]): IO[ExitCode] = {
    for {
      auth        <- extractTokens(args)
      decoder     <- CirceResponseDecoder[IO].pure[IO]
      backend     <- BlazeHttpBackend[IO]
      twippy      <- TwippyRestClient[IO](auth)(backend, decoder).pure[IO]
      _           <- print("Getting information about twitter account rate limits")
      rateLimits  <- twippy.rateLimits(List(Resource.Account))
      _           <- print("Account rate limits: " + rateLimits.resources.account.toString)
      _           <- backend.release
    } yield ExitCode.Success
  }

  private def extractTokens(args: List[String]): IO[Auth] = {
    val auth = for {
      consumerToken   <- args.get(0)
      consumerSecret  <- args.get(1)
      accessToken     <- args.get(2)
      accessSecret    <- args.get(3)
    } yield Auth(ConsumerToken(consumerToken, consumerSecret), AccessToken(accessToken, accessSecret))

    auth match {
      case Some(a) => IO.pure(a)
      case _       => IO.raiseError(new IllegalArgumentException("Provide twitter tokens!"))
    }
  }

  private def print[T: Show](input: T): IO[Unit] =
    IO(println(input.show))
}
