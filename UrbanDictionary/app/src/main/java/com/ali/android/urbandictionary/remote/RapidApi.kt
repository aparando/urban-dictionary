package com.ali.android.urbandictionary.remote

import com.ali.android.urbandictionary.remote.model.DictionaryApiResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RapidApi {
    @GET("define")
    fun getDefinitions(@Query("term") term: String): Call<DictionaryApiResult>
}
