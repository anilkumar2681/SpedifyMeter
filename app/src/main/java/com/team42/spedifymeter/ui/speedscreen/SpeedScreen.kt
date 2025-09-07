package com.team42.spedifymeter.ui.speedscreen

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.team42.spedifymeter.SpeedTestViewModel
import com.team42.spedifymeter.ui.customComponent.BottomNavBar
import com.team42.spedifymeter.ui.theme.DarkColor
import com.team42.spedifymeter.ui.theme.DarkColor2
import com.team42.spedifymeter.ui.theme.SpedifyMeterTheme

/**
 * Project: Spedify Meter
 * File: SpeedScreen.kt
 * Created By: ANIL KUMAR on 9/4/2025
 * Copyright © 2025 Team42. All rights reserved.
 **/
class SpeedScreenFragment : Fragment() {
    lateinit var viewModel: SpeedTestViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            viewModel = SpeedTestViewModel(Application())
            setContent {
                SpedifyMeterTheme {
                    SpeedScreen(viewModel)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpeedScreen(viewModel: SpeedTestViewModel) {
    val context = LocalContext.current
    val state by viewModel.state.collectAsState()
    var selectedTab by remember { mutableIntStateOf(0) }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    val title = when (selectedTab) {
                        0 -> "Speed Test"
                        1 -> "Status"
                        else -> "Settings"
                    }
                    Text(title, color = Color.White)
                },
            )
        },
        bottomBar = {
            BottomNavBar(
                selectedTab = selectedTab,
                onTabSelected = { selectedTab = it },

                )
        }
    ) { pad ->
        when (selectedTab) {
//            0 -> SpeedTestScreenContent(
//                uiState = uiState,
//                onStartTest = { viewModel.startSpeedTest() },
//                onResetTest = { viewModel.resetTest() },
//                modifier = Modifier.padding(pad),
//                //useDarkGradient = darkMode
//            )
//            1 -> StatusScreenContent(
//                uiState = uiState,
//                modifier = Modifier.padding(pad)
//            )


        }
    }

}

@Preview(showBackground = true)
@Composable
fun SpeedScreenPreview() {
    val viewModel = SpeedTestViewModel(Application())
    SpeedScreen(viewModel)
}