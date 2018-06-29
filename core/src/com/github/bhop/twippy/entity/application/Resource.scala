package com.github.bhop.twippy.entity.application

sealed trait Resource {

  import Resource._

  def value: String =
    this match {
      case Account => "account"
      case Application => "application"
      case Blocks => "blocks"
      case Collections => "collections"
      case Contacts => "contacts"
      case Device => "device"
      case DirectMessages => "direct_messages"
      case Favorites => "favorites"
      case Followers => "followers"
      case Friends => "friends"
      case Friendship => "friendship"
      case Geo => "geo"
      case Help => "help"
      case Lists => "lists"
      case Moments => "moments"
      case Mutes => "mutes"
      case SavedSearches => "saved_searches"
      case Search => "search"
      case Statuses => "statuses"
      case Trends => "trends"
      case Users => "users"
    }
}

object Resource {

  final case object Account extends Resource
  final case object Application extends Resource
  final case object Blocks extends Resource
  final case object Collections extends Resource
  final case object Contacts extends Resource
  final case object Device extends Resource
  final case object DirectMessages extends Resource
  final case object Favorites extends Resource
  final case object Followers extends Resource
  final case object Friends extends Resource
  final case object Friendship extends Resource
  final case object Geo extends Resource
  final case object Help extends Resource
  final case object Lists extends Resource
  final case object Moments extends Resource
  final case object Mutes extends Resource
  final case object SavedSearches extends Resource
  final case object Search extends Resource
  final case object Statuses extends Resource
  final case object Trends extends Resource
  final case object Users extends Resource
}
