package com.mitteloupe.whoami.test.server.response

import com.mitteloupe.whoami.test.server.MockResponse

interface MockResponseFactory {
    fun mockResponse(): MockResponse
}
