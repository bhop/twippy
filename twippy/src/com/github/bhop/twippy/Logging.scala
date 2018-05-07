package com.github.bhop.twippy

import cats.effect.Sync
import org.slf4j.Logger

private[twippy] trait Logging {

  private[twippy] val logger: Logger

  def info[F[_]](msg: String)(implicit F: Sync[F]): F[Unit] =
    F.delay(logger.info(msg))

  def debug[F[_]](msg: String)(implicit F: Sync[F]): F[Unit] =
    F.delay(logger.debug(msg))

  def warn[F[_]](msg: String)(implicit F: Sync[F]): F[Unit] =
    F.delay(logger.warn(msg))
}
