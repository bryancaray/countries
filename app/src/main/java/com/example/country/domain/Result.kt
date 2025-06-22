package com.example.country.domain

sealed class Result {
    data class Success<T>(val data: T) : Result()
    data class Failed(val errorCode: Int, val errorMesg: String, val errorSlug: String = "") : Result()
    object Loading : Result()
}

sealed class NetworkResult {
    data class Success<T>(val data: T) : NetworkResult()
    data class Failed(val errorCode: Int, val errorMesg: String, val errorSlug: String = "") : NetworkResult()
    object Unknown : NetworkResult()
    data class Exception(val errorMesg: String) : NetworkResult()
}

sealed class ViewResult {
    object Loading : ViewResult()
    object Success : ViewResult()
    data class Failed(val errorMesg: String,val errorCode: Int) : ViewResult()

}
