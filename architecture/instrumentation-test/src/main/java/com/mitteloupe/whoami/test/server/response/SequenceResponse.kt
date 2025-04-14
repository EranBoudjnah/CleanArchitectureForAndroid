package com.mitteloupe.whoami.test.server.response

import com.mitteloupe.whoami.test.server.ResponseBinder
import com.mitteloupe.whoami.test.server.ServerResponse

class SequenceResponse(private vararg val responses: MockResponseContents) : MockResponseContents {
    private var responseIndex = 0
    override fun mockResponse(responseBinder: ResponseBinder): ServerResponse {
        val mockResponse = responses[responseIndex]
        responseIndex++
        if (responseIndex == responses.size) {
            responseIndex = 0
        }
        return mockResponse.mockResponse(responseBinder)
    }
}
