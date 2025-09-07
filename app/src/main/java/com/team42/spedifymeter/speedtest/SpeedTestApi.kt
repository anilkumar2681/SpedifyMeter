package com.team42.spedifymeter.speedtest

import android.util.Log
import com.team42.spedifymeter.network.HttpClientProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.net.InetAddress
import kotlin.coroutines.cancellation.CancellationException
import kotlin.math.roundToInt
import kotlin.system.measureTimeMillis

/**
 * Project: Spedify Meter
 * File: SpeedTestApi.kt
 * Created By: ANIL KUMAR on 9/5/2025
 * Copyright © 2025 Team42. All rights reserved.
 **/

object SpeedTestApi {
    private val downloadUrls = listOf(
        "https://speed.hetzner.de/100MB.bin",
        "https://speedtest.tele2.net/100MB.zip"
    )

    private val uploadUrls = listOf(
        "https://your-upload-endpoint.com/upload"
    )

    private var currentJob: Job? = null

    fun runFullTest(
        scope: CoroutineScope,
        durationMs: Long = 5000,
        parallelStreams: Int = 2
    ): Flow<SpeedTestState> = channelFlow {
        cancel()
        currentJob = scope.launch {
            try {
                // 🔹 Phase 1: Ping
                send(SpeedTestState.Phase("Ping"))
                val ping = measurePing(HttpClientProvider.client)

                // 🔹 Phase 2: Download
                send(SpeedTestState.Phase("Download"))
                val download = SpeedTestEngine.measureSpeedTimed(
                    urls = downloadUrls,
                    durationMs = durationMs,
                    parallelStreams = parallelStreams,
                    isUpload = false,
                    scope = this,
                    onProgress = { progress -> trySend(SpeedTestState.Progress(progress, 0f)) },
                    onInstant = { speed -> trySend(SpeedTestState.Progress(0f, speed)) }
                )

                // 🔹 Phase 3: Upload
                send(SpeedTestState.Phase("Upload"))
                val upload = SpeedTestEngine.measureSpeedTimed(
                    urls = uploadUrls,
                    durationMs = durationMs,
                    isUpload = true,
                    scope = this,
                    onProgress = { progress -> trySend(SpeedTestState.Progress(progress, 0f)) },
                    onInstant = { speed -> trySend(SpeedTestState.Progress(0f, speed)) }
                )

                send(SpeedTestState.Completed(ping, download, upload))
            } catch (e: CancellationException) {
                send(SpeedTestState.Cancelled)
            } catch (e: Exception) {
                send(SpeedTestState.Error(e.message ?: "Unknown error"))
            }
        }
    }

    fun cancel() {
        currentJob?.cancel()
        HttpClientProvider.cancelAll()
    }

    private suspend fun measurePing(client: OkHttpClient): Long {
        val pingTimes = mutableListOf<Long>()

        val pingUrls = listOf(
            "https://httpbin.org/status/200",
            "https://www.google.com",
            "https://www.cloudflare.com"
        )

        repeat(3) { attempt ->
            val url = pingUrls[attempt % pingUrls.size]
            val startTime = System.currentTimeMillis()

            try {
                val request = Request.Builder()
                    .url(url)
                    .head()
                    .build()

                withContext(Dispatchers.IO) {
                    client.newCall(request).execute().use { response ->
                        if (response.isSuccessful) {
                            val endTime = System.currentTimeMillis()
                            val pingTime = endTime - startTime
                            if (pingTime > 0) {
                                pingTimes.add(pingTime)
                                Log.d("SpeedTestService", "Ping attempt ${attempt + 1}: ${pingTime} ms → $url")
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                Log.w("SpeedTestService", "Ping attempt ${attempt + 1} failed for $url: ${e.message}")
            }

            delay(200) // short pause between attempts
        }

        val averagePing = if (pingTimes.isNotEmpty()) {
            pingTimes.average().roundToInt()
        } else {
            50
        }

        Log.d("SpeedTestService", "Final Average Ping: $averagePing ms (from ${pingTimes.size} successful attempts)")
        return averagePing.toLong()
    }

}
