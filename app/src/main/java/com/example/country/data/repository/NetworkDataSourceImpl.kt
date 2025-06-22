package com.example.country.data.repository


import com.example.country.data.repository.NetworkDataSource
import com.example.country.data.network.ApiService
import com.example.country.domain.NetworkResult
import com.example.data.util.doIfFailed
import com.example.data.util.doIfSuccess
import com.example.data.util.safeApiCall
class NetworkDataSourceImpl(
    private val apiService: ApiService,
) : NetworkDataSource {
    override suspend fun getCountries(): NetworkResult {
        val response = safeApiCall {
            apiService.getCountries()
        }
        response?.doIfSuccess { data ->
            return NetworkResult.Success(data = data)
        }
        response.doIfFailed { errorCode, errorMsg ->
            return NetworkResult.Failed(errorCode = errorCode, errorMesg = errorMsg)
        }
        return NetworkResult.Unknown
    }

}