package com.weather.ui.home.step1.adapters

import android.app.Application
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.weather.R
import com.weather.data.models.WeatherResponse
import com.weather.databinding.ItemWeatherBinding

/**
 * Created by Tohamy on 30/03/2020
 */
class WeatherListAdapter : RecyclerView.Adapter<WeatherListAdapter.ViewHolder>() {
    private lateinit var weatherResponseList: ArrayList<WeatherResponse>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemWeatherBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_weather,
                parent,
                false
            )
        return ViewHolder(binding, parent.context)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(weatherResponseList[position])
    }

    override fun getItemCount(): Int {
        return if (::weatherResponseList.isInitialized) weatherResponseList.size else 0
    }

    fun setWeatherResponseList(weatherResponses: List<WeatherResponse>) {
        this.weatherResponseList = ArrayList()
        this.weatherResponseList.addAll(weatherResponses)
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: ItemWeatherBinding, context: Context) :
        RecyclerView.ViewHolder(binding.root) {
        private val weatherListViewModel =
            WeatherListViewModel(context.applicationContext as Application)

        fun bind(weatherResponse: WeatherResponse) {
            weatherListViewModel.bind(weatherResponse)
            binding.weatherListViewModel = weatherListViewModel
        }
    }
}