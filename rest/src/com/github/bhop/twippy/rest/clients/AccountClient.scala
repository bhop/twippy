package com.github.bhop.twippy.rest.clients

import com.github.bhop.twippy.core.entity.account.{Profile, Settings, User}
import com.github.bhop.twippy.core.http.HttpClient

private[rest] trait AccountClient[F[_]] {

  protected val http: HttpClient[F]

  def settings(): F[Settings] = ???

  def updateSettings(update: Settings.Update): F[Settings] = ???

  def verifyCredentials(): F[User] = ???

  def updateProfile(profile: Profile.Update): F[User] = ???

  def removeProfileBanner(): F[Unit] = ???
}
