package com.example.country.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.country.data.local.model.CountryEntity

@Dao
interface CountryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(posts: List<CountryEntity>)

    @Query("SELECT * FROM CountryEntity")
    fun getAllCountry(): List<CountryEntity>

    @Query("DELETE FROM CountryEntity")
    fun deleteAll()
}