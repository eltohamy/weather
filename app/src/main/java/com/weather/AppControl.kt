package com.weather

import android.app.Application
import android.content.Context
import com.weather.di.component.ApplicationComponent
import com.weather.di.component.DaggerApplicationComponent
import com.weather.di.module.NetworkModule

/**
 * Created by Tohamy on 30/03/2020
 */
class AppControl : Application() {

    private var mApplicationComponent: ApplicationComponent? = null

    val component: ApplicationComponent?
        get() {
            if (mApplicationComponent == null) {
                mApplicationComponent = DaggerApplicationComponent.builder()
                    .networkModule(NetworkModule())
                    .build()
            }
            return mApplicationComponent
        }

    companion object {
        operator fun get(context: Context): AppControl {
            return context.applicationContext as AppControl
        }
    }

}