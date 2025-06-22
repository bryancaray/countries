package com.example.country.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.country.data.local.model.CountryEntity


@Database(
    entities = [
        CountryEntity::class,
    ], version = 1, exportSchema = false
)

abstract class AppDatabase : RoomDatabase() {
    abstract fun countryDao(): CountryDao

    companion object {
        val DATABASE_NAME = "countries.db"
    }
}
