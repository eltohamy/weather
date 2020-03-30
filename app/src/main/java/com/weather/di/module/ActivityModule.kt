package com.weather.di.module


import android.app.Activity
import android.content.Context
import com.weather.di.ActivityContext
import dagger.Module
import dagger.Provides

/**
 * Created by Tohamy on 30/03/2020
 */
@Module
class ActivityModule(private val mActivity: Activity) {

    @Provides
    internal fun provideActivity(): Activity {
        return mActivity
    }

    @Provides
    @ActivityContext
    internal fun providesContext(): Context {
        return mActivity
    }
}
