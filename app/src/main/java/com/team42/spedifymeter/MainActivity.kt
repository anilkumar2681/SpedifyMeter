package com.team42.spedifymeter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.team42.spedifymeter.navigation.Routes
import com.team42.spedifymeter.navigation.SpedifyMeterNavGraph
import com.team42.spedifymeter.ui.onboarding.OnBoardingScreen
import com.team42.spedifymeter.ui.splash.SplashScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel: SpeedTestViewModel = hiltViewModel()
            val navController = rememberNavController()
            var startDestination by remember { mutableStateOf<String?>(null) }
            val onboardingDone by viewModel.isOnboardingDone.collectAsState()

            splashScreen.setKeepOnScreenCondition { startDestination == null }
            LaunchedEffect(Unit) {
                startDestination = if (onboardingDone) "home" else "onboard"
            }
            startDestination?.let { startDest ->
                SpedifyMeterNavGraph(navController, startDest, viewModel)
            }

        }
    }
}


