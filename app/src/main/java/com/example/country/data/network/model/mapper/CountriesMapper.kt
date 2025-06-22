package com.example.country.data.network.model.mapper

import androidx.annotation.Keep
import com.example.country.data.network.model.CountryReponse
import com.example.country.domain.model.Countries


@Keep
fun List<CountryReponse>.toDomain(): Countries {
    return Countries(this.map {
        Countries.Country(
            name = it.name,
            currency = it.currency?.name ?: "N/A",
            flag = it.flag,
            region = it.region,
            language = it.language.name,
            capital = it.capital,
            code = it.code
        )
    })
}