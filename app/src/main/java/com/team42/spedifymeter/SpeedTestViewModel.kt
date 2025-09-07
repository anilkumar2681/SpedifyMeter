package com.team42.spedifymeter

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.team42.spedifymeter.speedtest.SpeedTestApi
import com.team42.spedifymeter.speedtest.SpeedTestState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SpeedTestViewModel(application: Application) : AndroidViewModel(application) {
    private val _state = MutableStateFlow<SpeedTestState>(SpeedTestState.Phase("Idle"))
    val state: StateFlow<SpeedTestState> get() = _state

    fun startTest() {
        viewModelScope.launch {
            SpeedTestApi.runFullTest(
                scope = this,
                durationMs = 5000,
                parallelStreams = 2
            ).collect { result ->
                _state.value = result
            }
        }
    }

    fun cancelTest() {
        SpeedTestApi.cancel()
        _state.value = SpeedTestState.Cancelled
    }

}


data class SpeedTestUiState(
    val isTestRunning: Boolean = false,
    val progress: Float = 0f,
    val currentTest: String = "",
    val downloadSpeed: Float = 0f,
    val uploadSpeed: Float = 0f,
    val ping: Int = 0,
    val jitter: Float = 0f,
    val packetLoss: Float = 0f,
    val networkType: String = "",
    val hasResults: Boolean = false,
    val errorMessage: String? = null,
    val instantSamples: List<Float> = emptyList(),
    val minSpeed: Float = 0f,
    val avgSpeed: Float = 0f,
    val maxSpeed: Float = 0f
)
