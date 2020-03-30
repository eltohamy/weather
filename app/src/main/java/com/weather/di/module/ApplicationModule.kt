package com.weather.di.module


import android.app.Application
import android.content.Context
import com.weather.di.ApplicationContext
import dagger.Module
import dagger.Provides

/**
 * Created by Tohamy on 30/03/2020
 */
@Module
class ApplicationModule(private val mApplication: Application) {

    @Provides
    internal fun provideApplication(): Application {
        return mApplication
    }

    @Provides
    @ApplicationContext
    internal fun provideContext(): Context {
        return mApplication
    }
}
