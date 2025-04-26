package com.example.network.model

import com.google.gson.annotations.SerializedName

data class GeneralErrorNetworkModel(
    val code: Int?=null,
    @SerializedName("error_code")
    val errorCode: Int?=null,
    val message: String?=null,
    val endPoint: String?=null
)