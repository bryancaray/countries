package com.example.country.data.repository

import com.example.country.domain.NetworkResult


interface NetworkDataSource {
    suspend fun getCountries(): NetworkResult
}
