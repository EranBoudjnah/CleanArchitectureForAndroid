package com.mitteloupe.whoami.analytics.event

import com.mitteloupe.whoami.analytics.AnalyticsEvent

data class Click(
    private val buttonName: String,
    override val eventProperties: Map<String, Any> = emptyMap()
) : AnalyticsEvent(
    eventName = "Click: $buttonName",
    eventProperties = eventProperties
)
