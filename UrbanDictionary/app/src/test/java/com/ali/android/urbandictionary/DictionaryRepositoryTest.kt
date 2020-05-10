package com.ali.android.urbandictionary

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ali.android.urbandictionary.remote.model.DefinitionResult
import com.ali.android.urbandictionary.remote.model.DictionaryApiResult
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.SocketPolicy
import org.junit.Rule
import org.junit.Test
import java.net.HttpURLConnection

class DictionaryRepositoryTest {
    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    @Test
    fun `get Definitions 200 Success case`() = dictionaryRobot {
        val mockDictionaryApiResult = DictionaryApiResult(
            listOf(
                DefinitionResult(
                    definition = "hello",
                    likesCount = 40,
                    dislikesCount = 5
                ),
                DefinitionResult(
                    definition = "hi",
                    likesCount = 55,
                    dislikesCount = 12
                )
            )
        )
        mockServerWithCustomResponse(mockDictionaryApiResult)
    }.getDefinitions_200Success()

    @Test
    fun `get Definitions timed out`() = dictionaryRobot {
        mockServer {
            enqueue(MockResponse().setSocketPolicy(SocketPolicy.NO_RESPONSE))
        }
    }.getDefinitions_TimedOut()

    @Test
    fun `get Definitions failed`() = dictionaryRobot {
        mockServer {
            enqueue(MockResponse().setResponseCode(HttpURLConnection.HTTP_BAD_REQUEST))
        }
    }.getDefinitions_Failed()
}

