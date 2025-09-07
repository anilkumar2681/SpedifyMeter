package com.team42.spedifymeter.speedtest

/**
 * Project: Spedify Meter
 * File: SpeedTestState.kt
 * Created By: ANIL KUMAR on 9/5/2025
 * Copyright © 2025 Team42. All rights reserved.
 **/
sealed class SpeedTestState {
    object Idle : SpeedTestState()
    object Loading : SpeedTestState()
    data class Phase(val name: String) : SpeedTestState()
    data class Progress(val progress: Float, val instantSpeed: Float) : SpeedTestState()
    data class Completed(val ping: Long, val download: Float, val upload: Float) : SpeedTestState()
    data class Error(val message: String) : SpeedTestState()
    object Cancelled : SpeedTestState()
}
