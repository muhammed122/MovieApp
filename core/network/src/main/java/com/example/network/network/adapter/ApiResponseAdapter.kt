package com.example.network.network.adapter

import com.example.network.model.GeneralErrorNetworkModel
import com.example.network.network.NetworkResponseDelegate
import com.example.utils.resourceprovider.IResourceProvider
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Converter
import java.lang.reflect.Type

class ApiResponseAdapter<T : Any>(
    private val successType: Type,
    private val resourceProvider: IResourceProvider,
    private val errorConverter: Converter<ResponseBody, GeneralErrorNetworkModel>
) : CallAdapter<T, Call<T>> {

    override fun responseType(): Type = successType

    override fun adapt(call: Call<T>): Call<T> =
        NetworkResponseDelegate(call, errorConverter, resourceProvider)
}