package com.example.country.ui.model.mapper

import androidx.annotation.Keep
import com.example.country.data.network.model.CountriesResponse
import com.example.country.data.network.model.CountryReponse
import com.example.country.domain.model.Countries
import com.example.country.ui.model.CountriesModel


@Keep
fun List<Countries.Country>.toUIModel(): CountriesModel {
    return CountriesModel(this.map {
        CountriesModel.CountryModel(
            name = it.name,
            currency = it.currency,
            flag = it.flag,
            region = it.region,
            language = it.language,
            capital = it.capital,
            code = it.code
        )
    })
}

@Keep
fun CountriesModel.toDomain(): Countries {
    return Countries(this.countriesList.map {
        Countries.Country(
            name = it.name,
            currency = it.currency,
            flag = it.flag,
            region = it.region,
            language = it.language,
            capital = it.capital,
            code = it.code
        )
    })
}