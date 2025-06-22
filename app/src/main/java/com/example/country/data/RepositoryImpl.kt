package com.example.country.data


import com.example.country.data.local.CountryDao
import com.example.country.data.local.model.CountryEntity
import com.example.country.data.network.model.CountriesResponse
import com.example.country.data.network.model.CountryReponse
import com.example.country.data.network.model.mapper.toDomain
import com.example.country.data.repository.NetworkDataSource
import com.example.country.domain.NetworkResult
import com.example.country.domain.Result
import com.example.country.domain.model.Countries
import com.example.country.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class RepositoryImpl(
    private val networkDataSource: NetworkDataSource, private val countryDao: CountryDao
) : Repository {
    override suspend fun getCountry(): Flow<Result> = flow {
        emit(Result.Loading)
        when (val result = networkDataSource.getCountries()) {
            is NetworkResult.Failed -> {
                emit(
                    Result.Failed(
                        errorCode = result.errorCode, errorMesg = result.errorMesg
                    )
                )
            }

            NetworkResult.Unknown -> {
                emit(
                    Result.Failed(
                        errorCode = 0, errorMesg = "Unknown Error : verifyToken"
                    )
                )
            }

            is NetworkResult.Success<*> -> {
                val countryResponse = result.data as List<CountryReponse>
                emit(
                    Result.Success(
                        data = countryResponse.toDomain()
                    )
                )
            }

            is NetworkResult.Exception -> emit(
                Result.Failed(
                    errorCode = 0, errorMesg = result.errorMesg
                )
            )
        }
    }


    override suspend fun insertCountry(country: Countries) {
        countryDao.deleteAll()
        countryDao.insertAll(country.countriesList.map { country ->
            CountryEntity(
                name = country.name,
                code = country.code,
                currency = country.currency,
                flag = country.flag,
                language = country.language,
                capital = country.capital,
                region = country.region
            )
        })
    }

    override suspend fun getCountriesLocal(): Flow<Result> = flow {
        emit(Result.Loading)
        emit(
            Result.Success(
                Countries(countriesList = countryDao.getAllCountry().map {
                    Countries.Country(
                        name = it.name,
                        currency = it.currency,
                        flag = it.flag,
                        language = it.language,
                        capital = it.capital,
                        code = it.code,
                        region = it.region
                    )
                })
            )
        )
    }

}







