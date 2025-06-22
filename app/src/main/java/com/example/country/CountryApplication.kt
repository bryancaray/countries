package com.example.country

import android.app.Application
import com.example.country.di.AppModule
import com.example.country.di.AppModuleImpl
import com.example.country.di.DatabaseModule
import com.example.country.di.DatabaseModuleImpl
import com.example.country.di.NetworkModule
import com.example.country.di.NetworkModuleImpl
import com.example.country.di.PostExecutionThread
import com.example.country.di.PostExecutionThreadImpl


class CountryApplication : Application() {
    companion object {
        lateinit var networkModule: NetworkModule
        lateinit var appModule: AppModule
        lateinit var databaseModule: DatabaseModule
        val postExecutionThread: PostExecutionThread by lazy {
            PostExecutionThreadImpl()
        }
    }


    override fun onCreate() {
        super.onCreate()
        databaseModule = DatabaseModuleImpl(this)
        networkModule = NetworkModuleImpl(this)
        appModule = AppModuleImpl(networkModule.networkDataSource, databaseModule.countryDao)
    }
}