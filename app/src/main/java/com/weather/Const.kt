package com.weather

/**
 * Created by Tohamy on 30/03/2020
 */
interface Const {
    companion object {
        const val BASE_URL = "https://api.openweathermap.org/data/2.5/"
        const val API_KEY = "0e13ff0c8c0523df0cca1047f50a036b"
        const val METRIC = "metric"
        const val LOCATION_REQUEST = 100
        const val GPS_REQUEST = 101
    }
}