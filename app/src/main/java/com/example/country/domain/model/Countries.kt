package com.example.country.domain.model

data class Countries(
        val countriesList: List<Country>
) {
    data class Country(
        val name: String,
        val currency : String = "Unknown",
        val flag:String,
        val language:String,
        val region:String,
        val capital:String,
        val code:String
    )
}