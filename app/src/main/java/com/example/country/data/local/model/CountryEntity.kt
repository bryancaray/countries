package com.example.country.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CountryEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "currency") val currency: String,
    @ColumnInfo(name = "flag") val flag: String,
    @ColumnInfo(name = "language") val language: String,
    @ColumnInfo(name = "region") val region: String,
    @ColumnInfo(name = "capital") val capital: String,
    @ColumnInfo(name = "code") val code: String,
)