package com.example.country.data.network

import com.example.country.data.network.model.CountriesResponse
import com.example.country.data.network.model.CountryReponse
import com.example.country.domain.model.Countries
import retrofit2.Response
import retrofit2.http.GET


interface ApiService {

    @GET("countries.json")
    suspend fun getCountries(): Response<List<CountryReponse>>

}