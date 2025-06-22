package com.example.country.di

import android.content.Context
import androidx.room.Room
import com.example.country.BuildConfig
import com.example.country.data.local.AppDatabase
import com.example.country.data.local.CountryDao


interface DatabaseModule {
    val addDatabase: AppDatabase
    val countryDao: CountryDao
}

class DatabaseModuleImpl(private val appContext: Context) : DatabaseModule {
    override val addDatabase: AppDatabase by lazy {
        buildDatabase(appContext)
    }
    override val countryDao: CountryDao = addDatabase.countryDao()



}

private fun buildDatabase(appContext: Context): AppDatabase {
    if (BuildConfig.DEBUG) {
        return Room.databaseBuilder(
            appContext, AppDatabase::class.java, AppDatabase.DATABASE_NAME
        ).build()
    }
    val builder = Room.databaseBuilder(
        appContext.applicationContext, AppDatabase::class.java, AppDatabase.DATABASE_NAME
    )
    return builder.build()
}