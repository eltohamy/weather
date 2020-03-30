package com.weather.di.component


import com.weather.di.ConfigPersistent
import com.weather.di.module.ActivityModule
import dagger.Component

/**
 * Created by Tohamy on 30/03/2020
 */
@ConfigPersistent
@Component(dependencies = [ApplicationComponent::class])
interface ConfigPersistentComponent {
    fun activityComponent(activityModule: ActivityModule): ActivityComponent
}
