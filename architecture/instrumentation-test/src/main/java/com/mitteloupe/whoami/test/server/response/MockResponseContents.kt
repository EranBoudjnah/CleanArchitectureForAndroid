package com.mitteloupe.whoami.test.server.response

import com.mitteloupe.whoami.test.server.ResponseDispatcher
import com.mitteloupe.whoami.test.server.ServerResponse

interface MockResponseContents {
    fun mockResponse(responseDispatcher: ResponseDispatcher): ServerResponse
}
