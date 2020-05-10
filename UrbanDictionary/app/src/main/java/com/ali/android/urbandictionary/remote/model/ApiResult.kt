package com.ali.android.urbandictionary.remote.model

sealed class ApiResult<out T> {
    data class Success<out T>(val response: T) : ApiResult<T>()
    data class Failure(val httpException: Exception?) : ApiResult<Nothing>()
    object GenericError : ApiResult<Nothing>()
}
