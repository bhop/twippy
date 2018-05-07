package com.github.bhop.twippy.domain

import com.github.bhop.twippy.domain.parser.{Parser, StatusParser}

sealed trait Status

object Status {

  final case class User(id: Long,
                        name: String,
                        screen_name: String,
                        location: Option[String],
                        url: Option[String],
                        description: Option[String],
                       `protected`: Boolean,
                        verified: Boolean,
                        followers_count: Long,
                        friends_count: Long,
                        listed_count: Long,
                        favourites_count: Long,
                        statuses_count: Long,
                        created_at: String,
                        utc_offset: Option[Long],
                        geo_enabled: Boolean,
                        lang: String,
                        contributors_enabled: Boolean,
                        profile_background_color: String,
                        profile_background_image_url: String,
                        profile_background_image_url_https: String,
                        profile_background_tile: Boolean,
                        profile_banner_url: Option[String],
                        profile_image_url: String,
                        profile_image_url_https: String,
                        profile_link_color: String,
                        profile_sidebar_border_color: String,
                        profile_sidebar_fill_color: String,
                        profile_text_color: String,
                        profile_use_background_image: Boolean,
                        default_profile: Boolean,
                        default_profile_image: Boolean,
                        withheld_in_countries: Option[String],
                        withheld_scope: Option[String])

  final case class Tweet(created_at: String,
                   id: Long,
                   text: String,
                   source: String,
                   truncated: Boolean,
                   in_reply_to_status_id: Option[Long],
                   in_reply_to_user_id: Option[Long],
                   in_reply_to_screen_name: Option[String],
                   user: User,
//                   coordinates: Option[List[Coordinates]],
//                   place: Option[Place],
                   quoted_status_id: Option[Long],
                   is_quote_status: Boolean) extends Status

  final case class DeletionStatus(id: Long, user_id: Long)

  final case class Deletion(status: DeletionStatus) extends Status

  implicit val statusParser: Parser[Status] = StatusParser
}
