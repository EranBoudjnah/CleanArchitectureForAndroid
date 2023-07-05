package com.mitteloupe.whoami.test.server

import com.mitteloupe.whoami.test.server.response.MockResponseContents

data class MockRequestResponse(
    val request: MockRequest,
    val response: MockResponseContents
)
