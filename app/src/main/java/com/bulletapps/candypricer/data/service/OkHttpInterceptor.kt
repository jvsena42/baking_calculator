package com.bulletapps.candypricer.data.service

import com.bulletapps.candypricer.BuildConfig
import com.bulletapps.candypricer.data.datasource.PreferencesDataSource
import com.bulletapps.candypricer.presentation.util.PreferencesKeys
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class OkHttpInterceptor @Inject constructor(
    private val prefs: PreferencesDataSource
): Interceptor {


    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .addHeader("X-API-KEY", BuildConfig.BASE_URL)
            .addHeader("Authorization", "Bearer ${prefs.getPref(PreferencesKeys.ACCESS_TOKEN, "")}")
            .build()
        return chain.proceed(request)
    }
}