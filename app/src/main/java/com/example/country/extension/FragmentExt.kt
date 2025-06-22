package com.example.country.extension

import android.content.Context
import android.location.Geocoder
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import java.util.Locale

val Fragment.navController: NavController
    get() = requireView().findNavController()


fun Fragment.getCityName(lat: Double, long: Double): String {
    var cityName: String?
    val geoCoder = Geocoder(this.requireActivity(), Locale.getDefault())
    val address = geoCoder.getFromLocation(lat, long, 1)
    cityName = address!![0].adminArea
    if (cityName == null) {
        cityName = address[0].locality
        if (cityName == null) {
            cityName = address[0].subAdminArea
        }
    }
    return cityName
}

fun Fragment.isOnline(): Boolean {
    val connectivityManager =
        this.context!!.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    if (connectivityManager != null) {
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                return true
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                return true
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                return true
            }
        }
    }
    return false
}