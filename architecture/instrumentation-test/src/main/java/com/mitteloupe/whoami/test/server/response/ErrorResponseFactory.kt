package com.mitteloupe.whoami.test.server.response

import com.mitteloupe.whoami.test.server.MockResponse

sealed class ErrorResponseFactory {
    object NotFound : MockResponseFactory {
        override fun mockResponse() = MockResponse(code = 404)
    }
}
