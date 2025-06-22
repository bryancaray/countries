package com.example.data.util


import okhttp3.Interceptor
import okhttp3.Response

class RequestInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val url = chain.request()
            .url
            .newBuilder()
            /*.addQueryParameter("appid", BuildConfig.APIKEY)
            .addQueryParameter("units", RestConfig.UNIT)*/
            .build()
        val request = chain.request()
            .newBuilder()
            .url(url)
            .build()
        return  chain.proceed(request)

    }
}