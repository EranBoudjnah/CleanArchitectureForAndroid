package com.mitteloupe.whoami.analytics

abstract class AnalyticsEvent(val eventName: String, open val eventProperties: Map<String, Any>)
