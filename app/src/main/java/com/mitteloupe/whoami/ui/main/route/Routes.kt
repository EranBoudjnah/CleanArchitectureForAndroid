package com.mitteloupe.whoami.ui.main.route

import kotlinx.serialization.Serializable

@Serializable
object Home

@Serializable
data class History(val highlightedIpAddress: String?)
