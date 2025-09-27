package com.team42.spedifymeter.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.team42.spedifymeter.SpeedTestViewModel
import com.team42.spedifymeter.ui.onboarding.OnBoardingScreen
import com.team42.spedifymeter.ui.speedscreen.SpeedScreen

/**
 * Project: Spedify Meter
 * File: Routes.kt
 * Created By: ANIL KUMAR on 9/5/2025
 * Copyright © 2025 Team42. All rights reserved.
 **/
object Routes {
    const val SPLASH = "splash"
    const val ONBOARD = "onboard"
    const val MAIN = "main"
    const val HOME = "home"
    const val PROFILE = "profile"
    const val SETTINGS = "settings"
}

@Composable
fun SpedifyMeterNavGraph(
    navController: NavHostController,
    startDestination: String,
    viewModel: SpeedTestViewModel
) {
    NavHost(navController = navController, startDestination = startDestination) {
        composable(Routes.ONBOARD) {
            OnBoardingScreen(
                viewModel = viewModel,
                onFinished = {
                    navController.navigate(Routes.HOME) {
                        popUpTo(Routes.ONBOARD) { inclusive = true }
                    }
                }
            )
        }
        composable(Routes.HOME) { SpeedScreen(viewModel) }
    }
}

