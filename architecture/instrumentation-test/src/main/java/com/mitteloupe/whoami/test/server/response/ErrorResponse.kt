package com.mitteloupe.whoami.test.server.response

import com.mitteloupe.whoami.test.server.MockResponse

sealed class ErrorResponse {
    object NotFound : MockResponseContents {
        override fun mockResponse() = MockResponse(code = 404)
    }
}
