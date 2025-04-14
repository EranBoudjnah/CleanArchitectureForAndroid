package com.mitteloupe.whoami.server

import com.mitteloupe.whoami.constant.IP_ADDRESS
import com.mitteloupe.whoami.test.server.MockRequest
import com.mitteloupe.whoami.test.server.MockRequestResponseFactory
import com.mitteloupe.whoami.test.server.ResponseStore
import com.mitteloupe.whoami.test.server.response.SimpleResponseFactory

const val IPIFY_ENDPOINT = "/ipify/"
const val IPINFO_ENDPOINT = "/ipinfo/"

const val REQUEST_RESPONSE_GET_IP = "Get IP"
const val REQUEST_RESPONSE_GET_IP_DETAILS = "Get IP Details"

class AppResponseStore : ResponseStore() {
    override val internalResponseFactories = listOf(
        REQUEST_RESPONSE_GET_IP to MockRequestResponseFactory(
            request = MockRequest(IPIFY_ENDPOINT),
            responseFactory = SimpleResponseFactory(code = 200, bodyFileName = "api/get_ip.json")
        ),
        REQUEST_RESPONSE_GET_IP_DETAILS to MockRequestResponseFactory(
            request = MockRequest("${IPINFO_ENDPOINT}$IP_ADDRESS/geo"),
            responseFactory = SimpleResponseFactory(
                code = 200,
                bodyFileName = "api/get_ip_details.json"
            )
        )
    )
}
