package com.weather.data.models

data class City(

    val id: Int,
    val name: String,
    val coord: Coord,
    val country: String
)