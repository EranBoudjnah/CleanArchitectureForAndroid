package com.mitteloupe.whoami.test.server.response

import com.mitteloupe.whoami.test.server.ResponseDispatcher
import com.mitteloupe.whoami.test.server.ServerResponse

class SequenceResponse(private vararg val mockResponses: MockResponseContents) :
    MockResponseContents {
    private var responseIndex = 0
    override fun mockResponse(responseDispatcher: ResponseDispatcher): ServerResponse {
        val mockResponse = mockResponses[responseIndex]
        responseIndex++
        if (responseIndex == mockResponses.size) {
            responseIndex = 0
        }
        return mockResponse.mockResponse(responseDispatcher)
    }
}
