package com.weather.ui.home.step2.adapters

import android.app.Application
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.weather.R
import com.weather.data.models.ForecastList
import com.weather.databinding.ItemForecastBinding

/**
 * Created by Tohamy on 30/03/2020
 */
class ForecastListAdapter : RecyclerView.Adapter<ForecastListAdapter.ViewHolder>() {
    private lateinit var forecastList: ArrayList<ForecastList>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemForecastBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_forecast,
                parent,
                false
            )
        return ViewHolder(binding, parent.context)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(forecastList[position])
    }

    override fun getItemCount(): Int {
        return if (::forecastList.isInitialized) forecastList.size else 0
    }

    fun setForecastResponseList(forecastList: List<ForecastList>) {
        this.forecastList = ArrayList()
        this.forecastList.addAll(forecastList)
        notifyDataSetChanged()

    }

    class ViewHolder(private val binding: ItemForecastBinding, context: Context) :
        RecyclerView.ViewHolder(binding.root) {
        private val forecastListViewModel =
            ForecastListViewModel(context.applicationContext as Application)

        fun bind(forecastList: ForecastList) {
            forecastListViewModel.bind(forecastList)
            binding.forecastListViewModel = forecastListViewModel
        }
    }
}