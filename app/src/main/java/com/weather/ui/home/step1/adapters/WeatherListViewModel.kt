package com.weather.ui.home.step1.adapters

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.weather.R
import com.weather.data.models.WeatherResponse
import javax.inject.Inject

/**
 * Created by Tohamy on 30/03/2020
 */
class WeatherListViewModel @Inject
internal constructor(application: Application) : AndroidViewModel(application) {
    private val cityName = MutableLiveData<String>()
    private val minTemperature = MutableLiveData<String>()
    private val maxTemperature = MutableLiveData<String>()
    private val description = MutableLiveData<String>()
    private val windSpeed = MutableLiveData<String>()
    private lateinit var weatherResponse: WeatherResponse

    fun bind(weatherResponse: WeatherResponse) {
        this.weatherResponse = weatherResponse
        this.cityName.value = weatherResponse.name
        this.minTemperature.value =
            String.format(
                getString(R.string.min_temperature),
                weatherResponse.main.temp_min.toString()
            )
        this.maxTemperature.value =
            String.format(
                getString(R.string.max_temperature),
                weatherResponse.main.temp_max.toString()
            )
        this.description.value = weatherResponse.weather[0].description
        this.windSpeed.value =
            String.format(getString(R.string.wind_speed), weatherResponse.wind.speed.toString())
    }

    fun getCityName(): MutableLiveData<String> {
        return cityName
    }

    fun getMinTemperature(): MutableLiveData<String> {
        return minTemperature
    }

    fun getMaxTemperature(): MutableLiveData<String> {
        return maxTemperature
    }

    fun getDescription(): MutableLiveData<String> {
        return description
    }

    fun getWindSpeed(): MutableLiveData<String> {
        return windSpeed
    }

    private fun getString(resource: Int): String {
        return getApplication<Application>().resources.getString(resource)
    }
}