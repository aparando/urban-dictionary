package com.ali.android.urbandictionary.repository

import com.ali.android.urbandictionary.remote.model.ApiResult
import com.ali.android.urbandictionary.remote.model.DictionaryApiResult

interface DictionaryDataSource {
    suspend fun getDefinitions(term: String): ApiResult<DictionaryApiResult>
}
