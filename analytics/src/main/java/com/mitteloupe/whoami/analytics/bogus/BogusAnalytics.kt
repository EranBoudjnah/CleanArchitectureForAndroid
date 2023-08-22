package com.mitteloupe.whoami.analytics.bogus

import com.mitteloupe.whoami.analytics.Analytics
import com.mitteloupe.whoami.analytics.AnalyticsEvent

private var lastScreenName: String? = null

class BogusAnalytics : Analytics {
    override fun logScreen(screenName: String) {
        lastScreenName = screenName
        println("Event recorded: Entered $screenName")
    }

    override fun logEvent(event: AnalyticsEvent) {
        println("Event recorded: $lastScreenName -> ${event.eventName}")
        event.eventProperties.forEach { (eventKey, eventValue) ->
            println("$eventKey: $eventValue")
        }
    }
}
