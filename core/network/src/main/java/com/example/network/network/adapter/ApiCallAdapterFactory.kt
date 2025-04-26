package com.example.network.network.adapter

import com.example.network.model.GeneralErrorNetworkModel
import com.example.utils.resourceprovider.IResourceProvider
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class ApiCallAdapterFactory(
    private val resourceProvider: IResourceProvider
) : CallAdapter.Factory() {

    override fun get(
        returnType: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {

        check(returnType is ParameterizedType) {}
        val responseType = getParameterUpperBound(0, returnType)
        val errorBodyConverter = retrofit.responseBodyConverter<GeneralErrorNetworkModel>(
            GeneralErrorNetworkModel::class.java,
            annotations
        )

        return ApiResponseAdapter<Any>(responseType, resourceProvider, errorBodyConverter)

    }

}