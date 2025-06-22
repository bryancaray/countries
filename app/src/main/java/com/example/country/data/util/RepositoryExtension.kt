package com.example.data.util

import retrofit2.Response

inline fun <reified T> Response<T>.doIfSuccess(callback: (value: T) -> Unit) {
    if (this.isSuccessful) {
        callback(this.body()!!)
    }
}

inline fun <reified T> Response<T>?.doIfFailed(callback: (errorCode: Int, errorMsg: String) -> Unit) {
    if (this == null) {
        callback(0, "Something went wrong.")
    } else {
        callback(this.code(), this.message())
    }
}

inline fun <T> safeApiCall(responseFunction: () -> T): T? {
    return try {
        responseFunction.invoke()
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

