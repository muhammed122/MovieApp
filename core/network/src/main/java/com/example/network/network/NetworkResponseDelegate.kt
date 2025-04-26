package com.example.network.network

import com.example.network.R
import com.example.network.model.GeneralErrorNetworkModel
import com.example.network.utils.ApiException
import com.example.utils.resourceprovider.IResourceProvider
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Converter
import retrofit2.HttpException
import retrofit2.Response
import java.net.ConnectException
import java.net.HttpURLConnection
import java.net.SocketTimeoutException
import java.net.UnknownHostException

internal class NetworkResponseDelegate<T : Any>(
    private val delegate: Call<T>,
    private val errorConverter: Converter<ResponseBody, GeneralErrorNetworkModel>,
    private val resourceProvider: IResourceProvider

) : Call<T> by delegate {

    override fun enqueue(callback: Callback<T>) = delegate.enqueue(object : Callback<T> {
        override fun onResponse(call: Call<T>, response: Response<T>) {
            if (response.isSuccessful) callback.onResponse(
                this@NetworkResponseDelegate, Response.success(response.body())
            )
            else {
                callback.onFailure(
                    this@NetworkResponseDelegate, handleApiException(
                        resourceProvider, response.code(), response.errorBody()
                    )
                )
            }
        }

        override fun onFailure(call: Call<T>, t: Throwable) {
            callback.onFailure(this@NetworkResponseDelegate, parseApiException(t))

        }
    })

    private fun parseApiException(ex: Throwable): ApiException = when (ex) {
        is SocketTimeoutException,
        is ConnectException,
        is UnknownHostException,
        -> {
            ApiException.NoInternetConnection(
                message = resourceProvider.getText(R.string.no_internet_connection),
                throwable = ex)
        }
        is HttpException -> {
            handleApiException(
                resourceProvider,
                ex.code(),
                ex.response()?.errorBody()
            )
        }
        else -> ApiException.GeneralError(ex.message)
    }


    private fun handleApiException(
        resourceProvider: IResourceProvider,
        code: Int,
        errorBody: ResponseBody?,
    ): ApiException = when (code) {
        HttpURLConnection.HTTP_BAD_GATEWAY -> ApiException.GeneralError(
            resourceProvider.getText(R.string.error_server)
        )
        HttpURLConnection.HTTP_UNAUTHORIZED -> ApiException.Unauthorized

        HttpURLConnection.HTTP_GATEWAY_TIMEOUT -> {
            ApiException.TimeOut(resourceProvider.getText(R.string.error_server))
        }
        else -> {
            errorBody?.let {
                try {
                    val errorResponse =
                        errorConverter.convert(it)
                    ApiException.ApiError(
                        errorResponse?.message ?:
                        resourceProvider.getText(R.string.error_server),
                        statusCode = code,
                        errorCode = errorResponse?.errorCode
                    )

                } catch (e: Exception) {
                    ApiException.ApiError()
                }

            } ?: ApiException.GeneralError(resourceProvider.getText(R.string.error_server))
        }

    }
}

