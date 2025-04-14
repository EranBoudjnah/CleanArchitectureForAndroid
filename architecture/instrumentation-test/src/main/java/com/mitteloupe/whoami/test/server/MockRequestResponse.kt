package com.mitteloupe.whoami.test.server

import com.mitteloupe.whoami.test.server.response.MockResponseFactory

data class MockRequestResponse(val request: MockRequest, val response: MockResponseFactory)
