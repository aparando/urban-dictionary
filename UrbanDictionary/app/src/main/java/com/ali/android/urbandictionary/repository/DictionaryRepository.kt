package com.ali.android.urbandictionary.repository

import com.ali.android.urbandictionary.remote.RapidApi
import com.ali.android.urbandictionary.remote.model.ApiResult
import com.ali.android.urbandictionary.remote.model.DictionaryApiResult
import com.github.ajalt.timberkt.Timber
import ru.gildor.coroutines.retrofit.Result
import ru.gildor.coroutines.retrofit.awaitResult

class DictionaryRepository(private val api: RapidApi) : DictionaryDataSource {
    override suspend fun getDefinitions(term: String): ApiResult<DictionaryApiResult> {
        when (val apiResult = api.getDefinitions(term).awaitResult()) {
            is Result.Ok -> {
                return ApiResult.Success(apiResult.value)
            }
            is Result.Error -> {
                with(apiResult.exception) {
                    Timber.e(this) { "Error getting the definitions." }
                    return ApiResult.Failure(this)
                }
            }
            is Result.Exception -> {
                Timber.e(apiResult.exception) { "Unable to get definitions." }
                return ApiResult.GenericError
            }
        }

    }
}

