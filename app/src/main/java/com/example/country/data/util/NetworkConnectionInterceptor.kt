package com.example.data.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import okhttp3.Interceptor
import okhttp3.Response

class NetworkConnectionInterceptor : Interceptor {
    private lateinit var appContext : Context

    fun setContext (context: Context) {
        appContext = context
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        if (!isOnline())
            throw NoConnectivityException()
        return chain.proceed(chain.request())
    }

    private fun isOnline() : Boolean {
        return getConnectionType() > 0
    }

    private fun getConnectionType(): Int {
        var result = 0 // Returns connection type. 0: none; 1: mobile data; 2: wifi
        val cm = appContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            cm?.run {
                cm.getNetworkCapabilities(cm.activeNetwork)?.run {
                    when {
                        hasTransport(NetworkCapabilities.TRANSPORT_VPN) -> {
                            result = 3
                        }
                        hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                            result = 2
                        }
                        hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                            result = 1
                        }
                    }
                }
            }
        } else {
            cm?.run {
                cm.activeNetworkInfo?.run {
                    when (type) {
                        ConnectivityManager.TYPE_VPN -> {
                            result = 3
                        }
                        ConnectivityManager.TYPE_WIFI -> {
                            result = 2
                        }
                        ConnectivityManager.TYPE_MOBILE -> {
                            result = 1
                        }
                    }
                }
            }
        }
        return result
    }
}