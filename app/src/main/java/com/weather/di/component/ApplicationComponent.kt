package com.weather.di.component

import com.weather.data.DataManager
import com.weather.di.module.ApplicationModule
import com.weather.di.module.NetworkModule
import com.weather.ui.base.BaseActivity
import com.weather.ui.base.BaseFragment
import com.weather.ui.home.step1.Step1ViewModel
import com.weather.ui.home.step2.Step2ViewModel
import dagger.Component
import javax.inject.Singleton

/**
 * Created by Tohamy on 30/03/2020
 */
@Singleton
@Component(modules = [NetworkModule::class, ApplicationModule::class])
interface ApplicationComponent {

    fun dataManager(): DataManager
    fun inject(baseFragment: BaseFragment)
    fun inject(baseActivity: BaseActivity)

    /**
     * Injects required dependencies into the specified Step1ViewModel.
     * @param step1ViewModel Step1ViewModel in which to inject the dependencies
     */
    fun inject(step1ViewModel: Step1ViewModel)

    /**
     * Injects required dependencies into the specified Step2ViewModel.
     * @param step2ViewModel Step2ViewModel in which to inject the dependencies
     */
    fun inject(step2ViewModel: Step2ViewModel)

}

