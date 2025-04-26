package com.example.network.utils

sealed class ApiException(message: String? = null) : Exception(message) {

    class TimeOut(val code: String?) : ApiException(code)

    class NoInternetConnection(
        message: String?,
        throwable: Throwable? = null,
    ) : ApiException(message)

    object Unauthorized : ApiException(null)

    class ApiError(message: String? = null,val statusCode: Int? = null,val errorCode: Int? = null) : ApiException(message)

    class GeneralError(message: String?) : ApiException(message)
}