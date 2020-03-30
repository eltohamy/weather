package com.weather.data.models

data class ForecastResponse(
    val cod: Int,
    val message: Double,
    val cnt: Int,
    val list: List<ForecastList>,
    val city: City
)