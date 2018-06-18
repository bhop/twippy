package com.github.bhop.twippy.backend.blaze

import com.github.bhop.twippy.core.http.Auth.{AccessToken, ConsumerToken}
import org.http4s.client.oauth1.{Consumer, Token}

private[blaze] object implicits {

  implicit class ConsumerTokenOps(consumerToken: ConsumerToken) {
    def toConsumer: Consumer = Consumer(consumerToken.token, consumerToken.secret)
  }

  implicit class AccessTokenOps(accessToken: AccessToken) {
    def toToken: Token = Token(accessToken.token, accessToken.secret)
  }
}
