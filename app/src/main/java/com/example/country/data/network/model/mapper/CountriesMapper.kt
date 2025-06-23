package com.example.country.data.network.model.mapper

import androidx.annotation.Keep
import com.example.country.data.local.model.CountryEntity
import com.example.country.data.network.model.CountryReponse
import com.example.country.domain.model.Countries


@Keep
fun List<CountryReponse>.toDomainFromResponse(): Countries {
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


@Keep
fun List<CountryEntity>.toDomainFromEntity(): Countries {
    return Countries(this.map {
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


@Keep
fun Countries.Country.toEntity(): CountryEntity {
    return CountryEntity(
        name = this.name,
        code = this.code,
        currency = this.currency,
        flag = this.flag,
        language = this.language,
        capital = this.capital,
        region = this.region
    )
}
