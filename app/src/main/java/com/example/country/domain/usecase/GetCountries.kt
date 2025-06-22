package com.example.country.domain.usecase

import com.example.country.domain.Result
import com.example.country.domain.repository.Repository
import kotlinx.coroutines.flow.Flow

class GetCountriesUseCase(
    private val repository: Repository
) {

    suspend fun getCountries(
    ): Flow<Result> {
        return repository.getCountry()
    }
}
