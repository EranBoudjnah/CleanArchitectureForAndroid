package com.mitteloupe.whoami.datasource.ipaddress.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class IpAddressApiModel(
    @Json(name = "ip")
    val ipAddress: String
)
