package com.example.country.domain.usecase

import com.example.country.domain.Result
import com.example.country.domain.repository.Repository
import kotlinx.coroutines.flow.Flow


class GetLocalCountriesUseCase(
    private val repository: Repository
) {

    suspend fun getLocalCountries(): Flow<Result> {
        return repository.getCountriesLocal()
    }
}
