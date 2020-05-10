package com.ali.android.urbandictionary.remote.intercepter

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApiKeyInterceptor @Inject constructor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response = chain.proceed(
        with(chain.request().newBuilder()) {
            addHeader(
                "x-rapidapi-key",
                "3623bb92e3mshc2fa0c0df923510p1bd128jsn4dd1cfecfc94"
            )
        }.build()
    )
}
