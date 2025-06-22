package com.example.country.domain.repository

import com.example.country.domain.Result
import com.example.country.domain.model.Countries
import kotlinx.coroutines.flow.Flow

interface Repository {

    suspend fun getCountry(
    ): Flow<Result>

    suspend fun insertCountry(country: Countries)

    suspend fun getCountriesLocal(): Flow<Result>

}