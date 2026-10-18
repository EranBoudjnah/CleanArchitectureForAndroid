package com.mitteloupe.whoami.datasource.ipaddressinformation.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class IpAddressInformationApiModel(
    @get:Json(name = "city")
    val city: String? = null,

    @get:Json(name = "region")
    val region: String? = null,

    @get:Json(name = "country")
    val country: String? = null,

    @get:Json(name = "loc")
    val geolocation: String? = null,

    @get:Json(name = "org")
    val internetServiceProviderName: String? = null,

    @get:Json(name = "postal")
    val postCode: String? = null,

    @get:Json(name = "timezone")
    val timeZone: String? = null
)
