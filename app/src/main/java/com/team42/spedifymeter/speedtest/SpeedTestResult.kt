package com.team42.spedifymeter.speedtest

/**
 * Project: Spedify Meter
 * File: SpeedTestResult.kt
 * Created By: ANIL KUMAR on 9/5/2025
 * Copyright © 2025 Team42. All rights reserved.
 **/
sealed class SpeedTestResult {
    object Loading : SpeedTestResult()
    data class Success(
        val downloadSpeed: Float, // Mbps
        val uploadSpeed: Float,   // Mbps
        val ping: Int,            // ms
        val jitter: Float,        // ms
        val packetLoss: Float,    // percentage
        val networkType: String   // WiFi, Mobile, etc.
    ) : SpeedTestResult()
    data class Error(val message: String) : SpeedTestResult()
}