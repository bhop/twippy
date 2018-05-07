package com.github.bhop.twippy

import cats.effect.IO
import com.github.bhop.twippy.auth.{AccessToken, ConsumerToken}

object Test extends App {

  val consumerToken = ConsumerToken("HGyaQ6m5ih6EnaxWJMIGqvAYE", "pmeWvialYRwpUCg69x9uasKVIN4qvH3zLlizQV4OdApMPVHKkh")
  val accessToken = AccessToken("3022759941-Mvd1nPpIMbgekr2puousifNv6zfLvCvKRjY4cNn", "WjqgOszmYBY06L9HSiApD1rFHSNXHShVMDrCczbu5DJfw")

  val twitterStreamingClient = TwitterStreamingClient(consumerToken, accessToken)

  twitterStreamingClient
    .sampleStatuses[IO]
    .map(v => println(s"Element:  $v"))
    .compile
    .drain
    .unsafeRunSync()
}
