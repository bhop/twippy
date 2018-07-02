package com.github.bhop.twippy.entity.application

final case class RateLimits(rate_limit_context: RateLimits.RateLimitContext, resources: RateLimits.Resources)

object RateLimits {

  final case class RateLimitContext(access_token: String)

  final case class RateLimit(limit: Long, remaining: Long, reset: Long)

  type ResourceRateLimits = Option[Map[String, RateLimit]]

  final case class Resources(lists: ResourceRateLimits = None,
                             application: ResourceRateLimits = None,
                             mutes: ResourceRateLimits = None,
                             friendships: ResourceRateLimits = None,
                             blocks: ResourceRateLimits = None,
                             geo: ResourceRateLimits = None,
                             users: ResourceRateLimits = None,
                             followers: ResourceRateLimits = None,
                             collections: ResourceRateLimits = None,
                             statuses: ResourceRateLimits = None,
                             contacts: ResourceRateLimits = None,
                             moments: ResourceRateLimits = None,
                             help: ResourceRateLimits = None,
                             friends: ResourceRateLimits = None,
                             direct_messages: ResourceRateLimits = None,
                             account: ResourceRateLimits = None,
                             favorites: ResourceRateLimits = None,
                             device: ResourceRateLimits = None,
                             saved_searches: ResourceRateLimits = None,
                             search: ResourceRateLimits = None,
                             trends: ResourceRateLimits = None)
}
