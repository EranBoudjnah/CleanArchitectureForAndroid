package com.mitteloupe.whoami.datasource.ipaddress.service

import com.mitteloupe.whoami.datasource.ipaddress.model.IpAddressApiModel
import retrofit2.Call
import retrofit2.http.GET

interface IpAddressService {
    @GET("?format=json")
    fun ipAddress(): Call<IpAddressApiModel>
}
