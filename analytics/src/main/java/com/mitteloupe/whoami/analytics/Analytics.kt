package com.mitteloupe.whoami.analytics

interface Analytics {
    fun logScreen(screenName: String)

    fun logEvent(event: AnalyticsEvent)
}
