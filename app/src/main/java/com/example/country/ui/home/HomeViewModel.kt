package com.example.country.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.country.di.PostExecutionThread
import com.example.country.domain.usecase.GetCountriesUseCase
import com.example.country.domain.Result
import com.example.country.domain.model.Countries
import com.example.country.domain.usecase.GetLocalCountriesUseCase
import com.example.country.domain.usecase.InsertCountryUseCase
import com.example.country.extension.NetworkHelper
import com.example.country.ui.model.CountriesModel
import com.example.country.ui.model.mapper.toUIModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class HomeViewModel(
    private val networkHelper: NetworkHelper,
    private val getCountriesUseCase: GetCountriesUseCase,
    private val getLocalCountriesUseCase: GetLocalCountriesUseCase,
    private val insertCountryUseCase: InsertCountryUseCase,
    private val postExecutionThread: PostExecutionThread
) : ViewModel() {

    init {

        viewModelScope.launch {
            if (networkHelper.isConnected()) {
                getCountries()
            } else {
                getCountriesLocal()
            }
        }
    }

    private val _countries = MutableStateFlow<List<CountriesModel.CountryModel>>(emptyList())
    val getGetCountries: StateFlow<List<CountriesModel.CountryModel>> = _countries.asStateFlow()


    suspend fun getCountries() {
        withContext(postExecutionThread.io) {
            getCountriesUseCase.getCountries().collectLatest {
                when (it) {
                    is Result.Failed -> {

                    }

                    Result.Loading -> {

                    }

                    is Result.Success<*> -> {
                        val data = it.data as Countries
                        _countries.emit(data.countriesList.toUIModel().countriesList)
                        insertCountryUseCase.insertCountries(data)

                    }
                }
            }
        }

    }


    suspend fun getCountriesLocal() {
        withContext(postExecutionThread.io) {
            getLocalCountriesUseCase.getLocalCountries().collectLatest {
                when (it) {
                    is Result.Failed -> {

                    }

                    Result.Loading -> {

                    }

                    is Result.Success<*> -> {
                        val data = it.data as Countries
                        _countries.emit(data.countriesList.toUIModel().countriesList)
                        insertCountryUseCase.insertCountries(data)
                    }
                }
            }
        }
    }
}