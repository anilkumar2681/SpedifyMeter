package com.team42.spedifymeter.di

import android.content.Context
import com.team42.spedifymeter.data.DataStoreManager
import com.team42.spedifymeter.data.preferences.Prefs
import com.team42.spedifymeter.network.HttpClientProvider
import com.team42.spedifymeter.speedtest.SpeedTestEngine
import com.team42.spedifymeter.speedtest.SpeedTestService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import javax.inject.Singleton

/**
 * Project: Spedify Meter
 * File: AppModule.kt
 * Created By: ANIL KUMAR on 9/9/2025
 * Copyright © 2025 Team42. All rights reserved.
 **/
@Module
@InstallIn(SingletonComponent::class)
object PrefModule {

    @Provides
    @Singleton
    fun provideDataStoreManager(@ApplicationContext context: Context): DataStoreManager =
        DataStoreManager(context)

    @Provides
    @Singleton
    fun providePrefs(dsManager: DataStoreManager, @ApplicationContext context: Context): Prefs =
        Prefs(dsManager, context)
}