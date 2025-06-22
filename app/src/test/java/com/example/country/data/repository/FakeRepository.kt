package com.example.country.data.repository

import com.example.country.domain.model.Countries
import com.example.country.domain.repository.Repository
import com.example.country.domain.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeRepository : Repository {


    override suspend fun getCountry(): Flow<Result> {

        return flow {
            emit(
                Result.Success(
                    data = Countries(
                        countriesList = listOf(
                            Countries.Country(
                                "United States",
                                "$",
                                "sample_flag",
                                "English",
                                "NA",
                                "Washington, D.C.",
                                "US"
                            )
                        )
                    )
                )
            )
        }
    }

    override suspend fun insertCountry(country: Countries) {
        TODO("Not yet implemented")
    }

    override suspend fun getCountriesLocal(): Flow<com.example.country.domain.Result> {
        TODO("Not yet implemented")
    }
}