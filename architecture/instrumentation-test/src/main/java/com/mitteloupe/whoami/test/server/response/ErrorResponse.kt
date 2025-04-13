package com.mitteloupe.whoami.test.server.response

import com.mitteloupe.whoami.test.server.ResponseBinder
import com.mitteloupe.whoami.test.server.ServerResponse

sealed class ErrorResponse {
    object NotFound : MockResponseContents {
        override fun mockResponse(responseBinder: ResponseBinder) = ServerResponse(
            code = 404
        )
    }
}
