package com.weather.ui.home.step1

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.weather.Const
import com.weather.R
import com.weather.data.DataManager
import com.weather.data.models.WeatherResponse
import com.weather.ui.home.step1.adapters.WeatherListAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class Step1ViewModel @Inject
internal constructor(private val dataManager: DataManager) : ViewModel() {
    val weatherListAdapter: WeatherListAdapter = WeatherListAdapter()
    private var weatherResponseList: ArrayList<WeatherResponse> = ArrayList()
    private var compositeDisposable: CompositeDisposable = CompositeDisposable()
    var lastCitiesNames: List<String> = ArrayList()
    val errorMessage: MutableLiveData<Int> = MutableLiveData()
    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    val loadMoreVisibility: MutableLiveData<Int> = MutableLiveData()
    val errorClickListener = View.OnClickListener {
        loadWeatherForCities(lastCitiesNames)
    }

    init {
        loadingVisibility.value = View.GONE
    }

    fun loadWeatherForCities(cityNames: List<String>) {
        weatherResponseList = ArrayList()
        lastCitiesNames = cityNames
        for (cityName in cityNames)
            compositeDisposable.add(dataManager.remoteApi.getCurrentWeather(
                cityName,
                Const.METRIC,
                Const.API_KEY
            ).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { onSubscribe() }
                .doOnTerminate { onTerminate() }
                .subscribe(
                    { weatherResponse ->
                        if (weatherResponse != null) {
                            displayWeatherResponse(weatherResponse)
                        } else
                            onError()
                    },
                    { onError() }
                ))
    }

    private fun onSubscribe() {
        loadingVisibility.value = View.VISIBLE
        errorMessage.value = null
    }

    private fun onTerminate() {
        loadingVisibility.value = View.GONE
    }

    //set weather Response List to Adapter
    private fun displayWeatherResponse(weatherResponse: WeatherResponse) {
        weatherResponseList.add(weatherResponse)
        weatherListAdapter.setWeatherResponseList(weatherResponseList)
    }

    //display error message
    private fun onError() {
        errorMessage.value = R.string.error
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}