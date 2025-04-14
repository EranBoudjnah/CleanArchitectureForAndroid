package com.mitteloupe.whoami.test.server

import com.mitteloupe.whoami.test.server.response.MockResponseFactory

data class MockRequestResponseFactory(
    val request: MockRequest,
    val responseFactory: MockResponseFactory
)
