package com.mitteloupe.whoami.test.server.response

import com.mitteloupe.whoami.test.server.ResponseBinder
import com.mitteloupe.whoami.test.server.ServerResponse

interface MockResponseContents {
    fun mockResponse(responseBinder: ResponseBinder): ServerResponse
}
