package com.team42.spedifymeter.di

import com.team42.spedifymeter.network.HttpClientProvider
import com.team42.spedifymeter.speedtest.SpeedTestEngine
import com.team42.spedifymeter.speedtest.SpeedTestService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import javax.inject.Singleton

/**
 * Project: Spedify Meter
 * File: NetworkModule.kt
 * Created By: ANIL KUMAR on 9/9/2025
 * Copyright © 2025 Team42. All rights reserved.
 **/
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideHttpClientProvider(): HttpClientProvider = HttpClientProvider()

    @Provides
    @Singleton
    fun provideOkHttpClient(provider: HttpClientProvider): OkHttpClient = provider.client

    @Provides
    @Singleton
    fun provideSpeedTestEngine(client: OkHttpClient): SpeedTestEngine =
        SpeedTestEngine(client)

    @Provides
    @Singleton
    fun provideSpeedTestApi(
        client: OkHttpClient,
        engine: SpeedTestEngine
    ): SpeedTestService = SpeedTestService(client, engine)
}