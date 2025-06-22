package com.example.data.util


/*Format path to Valid OpenWeatherMap URL*/
fun String.formartImageUrl(): String {
    return "https://openweathermap.org/img/w/$this.png"
}
