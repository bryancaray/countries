package com.example.country.domain.usecase

import com.example.country.domain.model.Countries
import com.example.country.domain.repository.Repository


class InsertCountryUseCase(
    private val repository: Repository
) {

    suspend fun insertCountries(countries: Countries) {
        repository.insertCountry(countries)
    }
}
