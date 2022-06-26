package com.bulletapps.candypricer.data.service

import com.bulletapps.candypricer.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class OkHttpInterceptor: Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .addHeader("X-API-KEY", BuildConfig.BASE_URL)
            .build()
        return chain.proceed(request)
    }
}