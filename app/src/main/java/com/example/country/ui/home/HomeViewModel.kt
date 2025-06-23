package com.example.country.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.example.country.data.RepositoryImpl
import com.example.country.data.local.AppDatabase
import com.example.country.data.local.CountryDao
import com.example.country.data.network.ApiService
import com.example.country.data.repository.NetworkDataSourceImpl
import com.example.country.data.util.CurlLoggerInterceptor
import com.example.country.data.util.RestConfig
import com.example.country.domain.Result
import com.example.country.domain.model.Countries
import com.example.country.domain.usecase.GetCountriesUseCase
import com.example.country.domain.usecase.GetLocalCountriesUseCase
import com.example.country.domain.usecase.InsertCountryUseCase
import com.example.country.extension.NetworkHelper
import com.example.country.extension.PostExecutionThreadImpl
import com.example.country.ui.model.CountriesModel
import com.example.country.ui.model.mapper.toUIModel
import com.example.data.util.NetworkConnectionInterceptor
import com.example.data.util.RequestInterceptor
import com.google.gson.GsonBuilder
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val context = application.applicationContext

    // --- DATABASE SETUP ---
    private val countryDao: CountryDao by lazy {
        Room.databaseBuilder(
            context, AppDatabase::class.java, AppDatabase.DATABASE_NAME
        ).build().countryDao()
    }

    // --- NETWORK SETUP ---
    private val apiService: ApiService by lazy {
        val networkConnectionInterceptor = NetworkConnectionInterceptor().apply {
            setContext(context)
        }

        val okHttpClient = OkHttpClient.Builder().apply {
            if (RestConfig.DEBUG) {
                val logger = HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
                addInterceptor(logger)
            }
            readTimeout(100, TimeUnit.SECONDS)
            connectTimeout(100, TimeUnit.SECONDS)
            addInterceptor(RequestInterceptor())
            addInterceptor(networkConnectionInterceptor)
            addInterceptor(CurlLoggerInterceptor("CURL"))
        }.build()

        val retrofit = Retrofit.Builder().baseUrl(RestConfig.API_SERVER).client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .build()

        retrofit.create(ApiService::class.java)
    }

    // --- REPOSITORY & USE CASES ---
    private val repository = RepositoryImpl(
        NetworkDataSourceImpl(apiService), countryDao
    )
    private val getCountriesUseCase = GetCountriesUseCase(repository)
    private val getLocalCountriesUseCase = GetLocalCountriesUseCase(repository)
    private val insertCountryUseCase = InsertCountryUseCase(repository)

    // --- EXECUTION + NETWORK HELPERS ---
    private val postExecutionThread = PostExecutionThreadImpl()
    private val networkHelper = NetworkHelper(context)

    init {


        viewModelScope.launch {
            if (networkHelper.isConnected()) {
                getCountries()
            } else {
                _errorLiveData.value = "No Internet Connection"
                getCountriesLocal()
            }
        }
    }

    private val _errorLiveData = MutableLiveData<String>()
    val errorLiveData: LiveData<String> = _errorLiveData


    private val _countries = MutableStateFlow<List<CountriesModel.CountryModel>>(emptyList())
    val getGetCountries: StateFlow<List<CountriesModel.CountryModel>> = _countries.asStateFlow()


    suspend fun getCountries() {
        withContext(postExecutionThread.io) {
            getCountriesUseCase.getCountries().collectLatest {
                when (it) {
                    is Result.Failed -> {
                        _errorLiveData.postValue("Error: ${it.errorCode}")
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