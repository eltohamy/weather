package com.weather.data.remote

import com.weather.data.models.ForecastResponse
import com.weather.data.models.WeatherResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Tohamy on 30/03/2020
 */
interface RemoteApi {

    @GET("weather")
    fun getCurrentWeather(
        @Query("q") cityName: String?,
        @Query("units") units: String?,
        @Query("appid") appKey: String?
    ): Observable<WeatherResponse>

    @GET("forecast")
    fun getWeatherForecast(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") appKey: String?
    ): Observable<ForecastResponse>

}