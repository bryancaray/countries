package com.example.country.di

import com.example.country.data.RepositoryImpl
import com.example.country.data.local.CountryDao
import com.example.country.data.repository.NetworkDataSource
import com.example.country.domain.repository.Repository
import com.example.country.domain.usecase.GetCountriesUseCase
import com.example.country.domain.usecase.GetLocalCountriesUseCase
import com.example.country.domain.usecase.InsertCountryUseCase

interface AppModule {
    val repository: Repository
    val getCountriesUseCase: GetCountriesUseCase
    val getLocalCountriesUseCase: GetLocalCountriesUseCase
    val insertCountryUseCase: InsertCountryUseCase
}

class AppModuleImpl(
    networkDataSource: NetworkDataSource, countryDao: CountryDao
) : AppModule {
    override val repository: Repository by lazy {
        RepositoryImpl(networkDataSource = networkDataSource, countryDao = countryDao)
    }
    override val getCountriesUseCase: GetCountriesUseCase by lazy {
        GetCountriesUseCase(repository)
    }
    override val getLocalCountriesUseCase: GetLocalCountriesUseCase by lazy {
        GetLocalCountriesUseCase(repository)
    }
    override val insertCountryUseCase: InsertCountryUseCase by lazy {
        InsertCountryUseCase(repository)
    }


}
