package com.weather.data.models

data class ForecastList(

    val dt: Int,
    val main: Main,
    val weather: List<Weather>,
    val clouds: Clouds,
    val wind: Wind,
    val rain: Rain,
    val snow: Snow,
    val sys: Sys,
    val dt_txt: String,
    val visibility: Int
)