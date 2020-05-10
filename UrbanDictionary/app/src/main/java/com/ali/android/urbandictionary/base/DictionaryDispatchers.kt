package com.ali.android.urbandictionary.base

import kotlinx.coroutines.CoroutineDispatcher

data class DictionaryDispatchers(
    val ui: CoroutineDispatcher,
    val io: CoroutineDispatcher
)
