package com.weather.di.module

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.weather.Const
import com.weather.data.remote.RemoteApi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Created by Tohamy on 30/03/2020
 */
@Module
class NetworkModule {

    @Provides
    @Singleton
    internal fun provideGson(): Gson {
        val gsonBuilder = GsonBuilder()
        gsonBuilder.setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
        return gsonBuilder.create()
    }

    @Provides
    @Singleton
    internal fun providesInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)
    }


    @Provides
    @Singleton
    internal fun provideOkhttpClient(
        interceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(interceptor).build()
    }

    @Provides
    @Singleton
    internal fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(Const.BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    internal fun providesApiInterface(retrofit: Retrofit): RemoteApi {
        return retrofit.create<RemoteApi>(RemoteApi::class.java)
    }
}
