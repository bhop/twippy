package com.github.bhop.twippy.entity.help

import com.github.bhop.twippy.entity.help.Configuration.PhotoSizes

final case class Configuration(dm_text_character_limit: Long,
                               characters_reserved_per_media: Long,
                               max_media_per_upload: Long,
                               non_username_paths: List[String],
                               photo_size_limit: Long,
                               photo_sizes: PhotoSizes,
                               short_url_length: Long,
                               short_url_length_https: Long)

object Configuration {

  final case class PhotoSize(h: Long, resize: String, w: Long)

  final case class PhotoSizes(thumb: PhotoSize, small: PhotoSize, medium: PhotoSize, large: PhotoSize)
}