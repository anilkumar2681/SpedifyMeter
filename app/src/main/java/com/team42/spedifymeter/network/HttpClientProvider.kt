package com.team42.spedifymeter.network

/**
 * Project: Spedify Meter
 * File: HttpClientProvider.kt
 * Created By: ANIL KUMAR on 9/4/2025
 * Copyright © 2025 Team42. All rights reserved.
 **/

import okhttp3.Call
import okhttp3.OkHttpClient
import java.util.concurrent.CopyOnWriteArrayList
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class HttpClientProvider @Inject constructor() {
    private val activeCalls = CopyOnWriteArrayList<Call>()
    val client: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .addInterceptor { chain ->
                val call = chain.call()
                activeCalls.add(call)
                try {
                    chain.proceed(chain.request())
                } finally {
                    activeCalls.remove(call)
                }
            }
            .build()
    }

    fun cancelAll() {
        activeCalls.forEach { it.cancel() }
        activeCalls.clear()
    }
}
