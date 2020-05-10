package com.ali.android.urbandictionary

import com.ali.android.urbandictionary.remote.RapidApi
import com.ali.android.urbandictionary.remote.model.ApiResult
import com.ali.android.urbandictionary.repository.DictionaryRepository
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockWebServer
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class DictionaryTestResultRobot(private val api: RapidApi, private val server: MockWebServer) {
    private val repository by lazy(LazyThreadSafetyMode.NONE) {
        DictionaryRepository(api)
    }

    private fun makeApiCall(func: suspend () -> Unit) = runBlocking {
        try {
            func()
        } finally {
            server.shutdown()
        }
    }

    fun getDefinitions_200Success() = makeApiCall {
        val apiResult = repository.getDefinitions("")
        assertEquals(2, (apiResult as ApiResult.Success).response.result.size)

        with(apiResult.response) {
            assertEquals("hello", result[0].definition)
            assertEquals(40, result[0].likesCount)
            assertEquals(5, result[0].dislikesCount)

            assertEquals("hi", result[1].definition)
            assertEquals(55, result[1].likesCount)
            assertEquals(12, result[1].dislikesCount)
        }

    }

    fun getDefinitions_TimedOut() = makeApiCall {
        val apiResult = repository.getDefinitions("")
        assertEquals(apiResult, ApiResult.GenericError)
    }

    fun getDefinitions_Failed() = makeApiCall {
        val apiResult = repository.getDefinitions("")
        assertTrue { apiResult is ApiResult.Failure }
    }
}
