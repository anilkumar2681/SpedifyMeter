package com.team42.spedifymeter.speedtest

/**
 * Project: Spedify Meter
 * File: SpeedTestEngine.kt
 * Created By: ANIL KUMAR on 9/5/2025
 * Copyright © 2025 Team42. All rights reserved.
 **/

import kotlinx.coroutines.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okio.BufferedSink
import java.util.concurrent.atomic.AtomicLong
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.math.pow
import kotlin.random.Random

@Singleton
class SpeedTestEngine @Inject constructor(
    private val client: OkHttpClient // ✅ injected via Hilt
) {
    private val bufferSize = 64 * 1024 // 64KB

    suspend fun measureSpeedTimed(
        urls: List<String>,
        durationMs: Long,
        scope: CoroutineScope,
        parallelStreams: Int = 1,
        isUpload: Boolean = false,
        onProgress: (Float) -> Unit,
        onInstant: (Float) -> Unit
    ): Float = withContext(scope.coroutineContext) {

        val totalBytes = AtomicLong(0)
        val startTime = System.currentTimeMillis()

        val jobs = List(parallelStreams) { index ->
            async(Dispatchers.IO) {
                val url = urls[index % urls.size]
                val buffer = ByteArray(bufferSize)

                var attempt = 0
                while (isActive && System.currentTimeMillis() - startTime < durationMs) {
                    try {
                        val request = if (isUpload) {
                            val requestBody = object : RequestBody() {
                                override fun contentType() = "application/octet-stream".toMediaType()
                                override fun writeTo(sink: BufferedSink) {
                                    val random = Random(System.nanoTime())
                                    repeat(bufferSize / 1024) {
                                        val chunk = ByteArray(1024) { random.nextBytes(1)[0] }
                                        sink.write(chunk)
                                    }
                                }
                            }
                            Request.Builder().url(url).post(requestBody).build()
                        } else {
                            Request.Builder().url(url).build()
                        }

                        client.newCall(request).execute().use { response ->
                            if (!isUpload && response.isSuccessful) {
                                response.body?.byteStream()?.use { input ->
                                    var bytesRead: Int
                                    while (input.read(buffer).also { bytesRead = it } != -1) {
                                        totalBytes.addAndGet(bytesRead.toLong())
                                    }
                                }
                            }
                        }

                        attempt = 0 // ✅ reset backoff
                    } catch (_: Exception) {
                        attempt++
                        val backoff = (100L * 2.0.pow(attempt.toDouble()))
                            .toLong()
                            .coerceAtMost(5000L)
                        delay(backoff)
                    }
                }
            }
        }

        // progress ticker
        val progressJob = launch {
            while (isActive && System.currentTimeMillis() - startTime < durationMs) {
                delay(1000)
                val elapsed = (System.currentTimeMillis() - startTime).coerceAtLeast(1)
                val bytesSoFar = totalBytes.get()
                val speedMbps = (bytesSoFar * 8.0 / 1_000_000) / (elapsed / 1000.0)
                val progress = elapsed.toFloat() / durationMs
                onProgress(progress.coerceIn(0f, 1f))
                onInstant(speedMbps.toFloat())
            }
        }

        jobs.joinAll()
        progressJob.cancel()

        val totalElapsed = (System.currentTimeMillis() - startTime).coerceAtLeast(1)
        return@withContext ((totalBytes.get() * 8.0 / 1_000_000) / (totalElapsed / 1000.0)).toFloat()
    }
}

