package com.mitteloupe.whoami.test.server

data class ServerResponse(
    val upgradeToWebSocket: Boolean = false,
    val code: Int = 200,
    val headers: List<Pair<String, String>> = emptyList(),
    val body: String = ""
)
