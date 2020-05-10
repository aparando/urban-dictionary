package com.ali.android.urbandictionary

import com.ali.android.urbandictionary.remote.RapidApi
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.net.HttpURLConnection


fun dictionaryRobot(
    func: DictionaryTestRobot.() -> DictionaryTestResultRobot
) = DictionaryTestRobot().func()

class DictionaryTestRobot {
    fun mockServer(
        func: MockWebServer.() -> Unit
    ): DictionaryTestResultRobot {
        val server = MockWebServer().apply {
            func()
            start()
        }
        val baseUrl = server.url("/").toString()

        val retrofit = newRetrofit(baseUrl, newOkHttp(), newMoshi())

        val api = retrofit.create(RapidApi::class.java)

        return DictionaryTestResultRobot(api, server)
    }

    inline fun <reified T> mockServerWithCustomResponse(
        response: T,
        responseCode: Int = HttpURLConnection.HTTP_OK
    ): DictionaryTestResultRobot {
        val moshi = newMoshi()
        val body = moshi.adapter(T::class.java).toJson(response)
        val server = MockWebServer().apply {
            enqueue(MockResponse().setResponseCode(responseCode).setBody(body))
            start()
        }
        val baseUrl = server.url("/").toString()

        val retrofit = newRetrofit(baseUrl, newOkHttp(), moshi)
        val api = retrofit.create(RapidApi::class.java)

        return DictionaryTestResultRobot(api, server)
    }

}

// helper functions
fun newMoshi(): Moshi = Moshi.Builder().build()

fun newOkHttp(): OkHttpClient = OkHttpClient.Builder().build()

fun newRetrofit(
    baseUrl: String,
    okHttpClient: OkHttpClient,
    moshi: Moshi
): Retrofit = Retrofit.Builder()
    .baseUrl(baseUrl)
    .client(okHttpClient)
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .build()
