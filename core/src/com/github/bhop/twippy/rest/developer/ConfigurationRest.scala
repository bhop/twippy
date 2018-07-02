package com.github.bhop.twippy.rest.developer

import com.github.bhop.twippy.TwitterUris
import com.github.bhop.twippy.entity.help.Configuration
import com.github.bhop.twippy.http.{HttpBackend, ResponseDecoder}
import com.github.bhop.twippy.http.HttpBackend.{Auth, Get, HttpRequest, HttpUri}

private[twippy] trait ConfigurationRest[F[_]] {

  def auth: Auth
  def backend: HttpBackend[F]
  def decoder: ResponseDecoder[F]

  /**
    * Returns the current configuration used by Twitter including twitter.com slugs which are not usernames, maximum photo resolutions, and t.co shortened URL length.
    * It is recommended applications request this endpoint when they are loaded, but no more than once a day.
    *
    * For more information see:
    * <a href="https://developer.twitter.com/en/docs/developer-utilities/configuration/api-reference/get-help-configuration" target="_blank">
    *   Get Twitter configuration details</a>
    *
    * @return Current configuration details
    */
  def currentConfiguration: F[Configuration] =
    backend.send[Configuration](currentConfigurationRequest)(decoder.decodeConfiguration)

  private def currentConfigurationRequest: HttpRequest =
    Get(HttpUri(TwitterUris.twitterConfiguration), auth)
}
