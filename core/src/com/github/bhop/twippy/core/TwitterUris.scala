package com.github.bhop.twippy.core

object TwitterUris {

  private[twippy] val twitterRestUri: String = "https://api.twitter.com/1.1"

  private[twippy] val twitterRateLimitsUri: String = twitterRestUri + "/application/rate_limit_status.json"
}
