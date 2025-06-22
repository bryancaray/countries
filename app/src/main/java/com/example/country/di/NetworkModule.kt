package com.example.country.di

import android.content.Context
import com.example.country.data.RepositoryImpl
import com.example.country.data.repository.NetworkDataSource
import com.example.country.domain.repository.Repository
import com.example.country.data.network.ApiService
import com.example.country.data.repository.NetworkDataSourceImpl
import com.example.country.data.util.CurlLoggerInterceptor
import com.example.data.util.NetworkConnectionInterceptor
import com.example.data.util.RequestInterceptor
import com.example.country.data.util.RestConfig
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


/*
object NetworkModule {






    @Provides
    @Singleton
    fun provideSysNetworkDataSource(
        apiService: ApiService
    ): NetworkDataSource =
        NetworkDataSourceImpl(
            apiService = apiService
        )

    @Provides
    @Singleton
    fun Repository(
        networkDataSource: NetworkDataSource,
        postDao: PostDao
    ): Repository =
        RepositoryImpl(
            service = networkDataSource,
            postDao = postDao
        )


}*/



interface NetworkModule {
    val gson: Gson
    val okHttpClient: OkHttpClient
    val retrofit: Retrofit
    val apiService: ApiService
    val networkDataSource:NetworkDataSource
}

class NetworkModuleImpl(
    private val appContext: Context
): NetworkModule {
    override val gson: Gson by lazy {
        GsonBuilder().setLenient().create()
    }
    override val okHttpClient: OkHttpClient by lazy {
        val networkConnectionInterceptor = NetworkConnectionInterceptor()
        networkConnectionInterceptor.setContext(context = appContext)

        if (RestConfig.DEBUG) { // debug ON
            val logger = HttpLoggingInterceptor()
            logger.level = HttpLoggingInterceptor.Level.BODY
            OkHttpClient.Builder()
                .addInterceptor(logger)
                .readTimeout(100, TimeUnit.SECONDS)
                .addInterceptor(RequestInterceptor())
                .addInterceptor(networkConnectionInterceptor)
                .addInterceptor(CurlLoggerInterceptor("CURL"))
                .connectTimeout(100, TimeUnit.SECONDS)
                .build()
        } else // debug OFF
            OkHttpClient.Builder()
                .readTimeout(100, TimeUnit.SECONDS)
                .addInterceptor(RequestInterceptor())
                .addInterceptor(networkConnectionInterceptor)
                .addInterceptor(CurlLoggerInterceptor("CURL"))
                .connectTimeout(100, TimeUnit.SECONDS)
                .build()

    }
    override val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(RestConfig.API_SERVER)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    override val apiService : ApiService = retrofit.create(ApiService::class.java)
    override val networkDataSource: NetworkDataSource by lazy {
        NetworkDataSourceImpl(
            apiService = apiService
        )
    }

}
