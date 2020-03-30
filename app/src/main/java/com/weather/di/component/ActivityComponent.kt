package com.weather.di.component

import com.weather.di.PerActivity
import com.weather.di.module.ActivityModule
import com.weather.ui.base.BaseFragment
import com.weather.ui.home.step1.Step1Fragment
import com.weather.ui.home.step2.Step2Fragment
import dagger.Subcomponent

/**
 * Created by Tohamy on 30/03/2020
 */
@PerActivity
@Subcomponent(modules = [ActivityModule::class])
interface ActivityComponent {
    fun inject(baseFragment: BaseFragment)
    fun inject(step1Fragment: Step1Fragment)
    fun inject(step2Fragment: Step2Fragment)
}