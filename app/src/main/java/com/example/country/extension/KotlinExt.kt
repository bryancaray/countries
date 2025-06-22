package com.example.country.extension


import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.fragment.app.Fragment
import java.text.DecimalFormat

/**
 * Formats double to currency format using the given [currency]
 * example:
 *    val num: Double = 1126
 *    num.toCurrencyFormat("$)
 *
 *    returns $ 1,126.00
 *
 *  @return formatted currency value in string
 */
fun Double?.toCurrencyFormat(currency: String): String {
    val formatter = DecimalFormat("###,###,##0.00")
    return "$currency ${formatter.format(this)}"
}

/**
 * Formats double to currency format using the given
 *  @return equivalent double value of the currency value using the [toCurrencyFormat]
 */
fun String?.clearCurrencyFormat(): Double {
    if(isNullOrEmpty()) return 0.0
    val numberPart = this.split(" ").lastOrNull()?: return 0.0
    return numberPart.toNumberString().toDouble()
}

/**
 *  Removes all "," and converts blank as "0"
 */
fun CharSequence?.toNumberString() : String {
    if(isNullOrEmpty()) return "0"
    return toString().replace(",", "")
}


/**
 * Extract all Int in String
 */
fun String.extractNumbersFromString(): List<Int> {
    return this.split("\\D+".toRegex())
        .filter { it.isNotEmpty() }
        .map { it.toInt() }
}
// 2 version. check if v2 is "Higher,Lower or Equal"

val v1="2.3"
val v2="2.3.1"


data class Version(
    val major:Int,
    val minor:Int,
    val patch:Int
)

fun check():String{
    val v1Array = v1.split(".").map {
        it.toInt()
    }

    val v2Array = v2.split(".").map {
        it.toInt()
    }

    val version1 = convert(v1Array)
    val version2 = convert(v2Array)

    if(version1.major == version2.major){
        return if(version1.minor == version2.minor){
            if(version1.patch == version2.patch){
                "Equal"
            }else if(version1.patch < version2.patch){
                "Higher"
            }else{
                "Lower"
            }
        }else if(version1.minor < version2.minor){
            "Higher"
        }else{
            "Lower"
        }
    }else if(version1.major < version2.major){
        return "Higher"
    }
    return "Lower"
}


fun convert(version:List<Int>):Version{
    return when(version.size){
        3-> Version(version[0],version[1],version[2])
        2-> Version(version[0],version[1],0)
        1-> Version(version[0],0,0)
        else->Version(0,0,0)
    }


}



