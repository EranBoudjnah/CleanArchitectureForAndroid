package com.mitteloupe.whoami.datasource.ipaddressinformation.service

import com.mitteloupe.whoami.datasource.ipaddressinformation.model.IpAddressInformationApiModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface IpAddressInformationService {
    @GET("{ipAddress}/geo")
    fun ipAddressInformation(
        @Path("ipAddress") ipAddress: String
    ): Call<IpAddressInformationApiModel>
}
