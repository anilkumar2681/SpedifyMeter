package com.team42.spedifymeter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.team42.spedifymeter.navigation.Routes
import com.team42.spedifymeter.navigation.SpedifyMeterNavGraph
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel: SpeedTestViewModel = hiltViewModel()
            val navController = rememberNavController()
            val onboardingDone by viewModel.isOnboardingDone.collectAsState()

            if (onboardingDone == null) {
                splashScreen.setKeepOnScreenCondition { true }
            } else {
                splashScreen.setKeepOnScreenCondition { false }
                SpedifyMeterNavGraph(
                    navController = navController,
                    startDestination = if (onboardingDone == true) Routes.HOME else Routes.ONBOARD,
                    viewModel = viewModel
                )
            }
        }
    }
}


