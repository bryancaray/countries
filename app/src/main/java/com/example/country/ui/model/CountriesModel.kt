package com.example.country.ui.model

data class CountriesModel(
        val countriesList: List<CountryModel>
) {
    data class CountryModel(
        val name: String,
        val currency : String,
        val flag:String,
        val language:String,
        val region:String,
        val capital:String,
        val code:String
    )
}