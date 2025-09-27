package com.team42.spedifymeter

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.team42.spedifymeter.data.DataStoreManager
import com.team42.spedifymeter.data.preferences.PrefsKeys
import com.team42.spedifymeter.speedtest.SpeedTestService
import com.team42.spedifymeter.speedtest.SpeedTestEngine
import com.team42.spedifymeter.speedtest.SpeedTestState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SpeedTestViewModel @Inject constructor(
    private val speedTestService: SpeedTestService,
    private val dataStore: DataStoreManager
) : ViewModel() {
    private val _state = MutableStateFlow<SpeedTestState>(SpeedTestState.Phase("Idle"))
    val state: StateFlow<SpeedTestState> get() = _state
    private val _isOnboardingDone = MutableStateFlow(false)
    val isOnboardingDone: StateFlow<Boolean> = _isOnboardingDone.asStateFlow()

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

    fun isUserOnboarded(){
        viewModelScope.launch {
            dataStore.getPreference(PrefsKeys.OnboardingDone, false).collect {
                _isOnboardingDone.value = it
            }
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
