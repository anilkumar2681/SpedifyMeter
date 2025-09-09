package com.team42.spedifymeter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.team42.spedifymeter.navigation.Routes
import com.team42.spedifymeter.ui.onboarding.OnBoardingScreen
import com.team42.spedifymeter.ui.splash.SplashScreen
import com.team42.spedifymeter.ui.theme.SpedifyMeterTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SpedifyMeterTheme {
                AppNavigation()
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.SPLASH
    ) {
        composable(Routes.SPLASH) {
            SplashScreen()
        }
        composable(Routes.ONBOARD) {
            OnBoardingScreen()
        }
    }
}

