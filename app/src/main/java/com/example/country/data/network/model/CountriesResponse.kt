package com.example.country.data.network.model

import android.icu.util.Currency
import androidx.annotation.Keep

@Keep
data class CountriesResponse(
    val countries:List<CountryReponse>
)

data class CountryReponse(
    val capital: String,
    val code: String,
    val currency: CurrencyReponse?,
    val flag: String,
    val language: LanguageReponse,
    val name: String,
    val region: String
)

data class CurrencyReponse(
    val code: String,
    val name: String,
    val symbol: String
)

data class LanguageReponse(
    val code: String,
    val name: String
)