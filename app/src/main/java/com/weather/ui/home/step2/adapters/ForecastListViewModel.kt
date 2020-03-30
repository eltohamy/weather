package com.weather.ui.home.step2.adapters

import android.app.Application
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.weather.R
import com.weather.data.models.ForecastList
import javax.inject.Inject

/**
 * Created by Tohamy on 30/03/2020
 */
class ForecastListViewModel @Inject
internal constructor(application: Application) : AndroidViewModel(application) {
    private val dateText = MutableLiveData<String>()
    private val minTemperature = MutableLiveData<String>()
    private val maxTemperature = MutableLiveData<String>()
    private val description = MutableLiveData<String>()
    private val windSpeed = MutableLiveData<String>()
    private val groupVisibility = MutableLiveData<Int>()
    private lateinit var forecastList: ForecastList
    val showHideClickListener = View.OnClickListener {
        if (this.groupVisibility.value == View.GONE) this.groupVisibility.value =
            View.VISIBLE else this.groupVisibility.value = View.GONE
    }

    fun bind(forecastList: ForecastList) {
        this.forecastList = forecastList
        this.groupVisibility.value = View.GONE
        this.dateText.value = forecastList.dt_txt
        this.minTemperature.value =
            String.format(
                getString(R.string.min_temperature),
                forecastList.main.temp_min.toString()
            )
        this.maxTemperature.value =
            String.format(
                getString(R.string.max_temperature),
                forecastList.main.temp_max.toString()
            )
        this.description.value = forecastList.weather[0].description
        this.windSpeed.value =
            String.format(getString(R.string.wind_speed), forecastList.wind.speed.toString())
    }

    fun getDateText(): MutableLiveData<String> {
        return dateText
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

    fun getGroupVisibility(): MutableLiveData<Int> {
        return groupVisibility
    }

    private fun getString(resource: Int): String {
        return getApplication<Application>().resources.getString(resource)
    }
}