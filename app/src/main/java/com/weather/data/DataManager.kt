package com.weather.data

import com.weather.data.remote.RemoteApi
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Tohamy on 30/03/2020
 */
@Singleton
class DataManager @Inject
constructor(val remoteApi: RemoteApi)
