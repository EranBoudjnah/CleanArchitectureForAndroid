package com.mitteloupe.whoami.test.server.response

import com.mitteloupe.whoami.test.server.ResponseDispatcher
import com.mitteloupe.whoami.test.server.ServerResponse

sealed class ErrorResponse {
    object NotFound : MockResponseContents {
        override fun mockResponse(responseDispatcher: ResponseDispatcher) = ServerResponse(
            code = 404
        )
    }
}
