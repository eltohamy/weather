package com.weather.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.weather.data.DataManager
import com.weather.ui.home.step1.Step1ViewModel

/**
 * Created by Tohamy on 30/03/2020
 */
class Step1ViewModelFactory(private val dataManager: DataManager) :
    ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return Step1ViewModel(dataManager) as T
    }
}