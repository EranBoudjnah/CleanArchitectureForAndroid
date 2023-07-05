package com.mitteloupe.whoami.datasource.ipaddressinformation.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class IpAddressInformationApiModel(
    @Json(name = "city")
    val city: String? = null,

    @Json(name = "region")
    val region: String? = null,

    @Json(name = "country")
    val country: String? = null,

    @Json(name = "loc")
    val geolocation: String? = null,

    @Json(name = "org")
    val internetServiceProviderName: String? = null,

    @Json(name = "postal")
    val postCode: String? = null,

    @Json(name = "timezone")
    val timeZone: String? = null
)
