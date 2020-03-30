package com.weather.ui.home.step2

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.weather.Const
import com.weather.R
import com.weather.data.DataManager
import com.weather.data.models.ForecastResponse
import com.weather.ui.home.step2.adapters.ForecastListAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class Step2ViewModel @Inject
internal constructor(private val dataManager: DataManager) : ViewModel() {
    private var lat: Double = 0.0
    private var lon: Double = 0.0
    val forecastListAdapter: ForecastListAdapter = ForecastListAdapter()
    private var compositeDisposable: CompositeDisposable = CompositeDisposable()
    val cityName: MutableLiveData<String> = MutableLiveData()
    val errorMessage: MutableLiveData<Int> = MutableLiveData()
    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    val loadMoreVisibility: MutableLiveData<Int> = MutableLiveData()
    val errorClickListener = View.OnClickListener {
        loadWeatherForecast(lat, lon)
    }

//    init {
//        loadWeatherForecast(25.26, 55.3)
//    }

    fun loadWeatherForecast(lat: Double, lon: Double) {
        this.lat = lat
        this.lon = lon
        compositeDisposable.add(dataManager.remoteApi.getWeatherForecast(
            lat, lon,
            Const.API_KEY
        ).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onSubscribe() }
            .doOnTerminate { onTerminate() }
            .subscribe(
                { forecastResponse ->
                    if (forecastResponse != null) {
                        displayForecastResponse(forecastResponse)
                    } else
                        onError()
                },
                {
                    onError()
                }
            ))
    }

    private fun onSubscribe() {
        loadingVisibility.value = View.VISIBLE
        errorMessage.value = null
    }

    private fun onTerminate() {
        loadingVisibility.value = View.GONE
    }

    //set forecast response List to Adapter
    private fun displayForecastResponse(forecastResponse: ForecastResponse) {
        cityName.value = String.format("%s (%3f,%3f))", forecastResponse.city.name, lat, lon)
        forecastListAdapter.setForecastResponseList(forecastResponse.list)
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