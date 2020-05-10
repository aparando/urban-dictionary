package com.ali.android.urbandictionary.remote.model

import com.squareup.moshi.Json

data class DictionaryApiResult(@field:Json(name = "list") val result: List<DefinitionResult>)

data class DefinitionResult(
    val definition: String,
    @field:Json(name = "thumbs_up") val likesCount: Int,
    @field:Json(name = "thumbs_down") val dislikesCount: Int
)
