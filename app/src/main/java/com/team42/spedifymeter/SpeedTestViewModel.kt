package com.team42.spedifymeter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.team42.spedifymeter.data.preferences.Prefs
import com.team42.spedifymeter.speedtest.SpeedTestService
import com.team42.spedifymeter.speedtest.SpeedTestState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SpeedTestViewModel @Inject constructor(
    private val speedTestService: SpeedTestService,
    private val prefs: Prefs,
) : ViewModel() {
    private val _state = MutableStateFlow<SpeedTestState>(SpeedTestState.Phase("Idle"))
    val state: StateFlow<SpeedTestState> get() = _state
    val isOnboardingDone: StateFlow<Boolean?> = prefs.onboardingDone
        .map { it as Boolean? }
        .stateIn(viewModelScope, SharingStarted.Eagerly, null)

    fun startTest() {
        viewModelScope.launch {
            speedTestService.runFullTest(
                scope = this,
                durationMs = 5000,
                parallelStreams = 2
            ).collect { result ->
                _state.value = result
            }
        }
    }

    fun cancelTest() {
        speedTestService.cancel()
        _state.value = SpeedTestState.Cancelled
    }

    fun completeOnboarding(onDone: () -> Unit) {
        viewModelScope.launch {
            prefs.setOnboardingDone(true)
            onDone()
        }
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
