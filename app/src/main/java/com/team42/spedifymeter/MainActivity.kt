package com.team42.spedifymeter

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.team42.spedifymeter.navigation.Routes
import com.team42.spedifymeter.ui.splash.SplashScreen
import com.team42.spedifymeter.ui.theme.SpedifyMeterTheme

class MainActivity : AppCompatActivity() {
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
            SplashScreen(
                onNavigate = { isFirstTime ->
                    if (isFirstTime) navController.navigate(Routes.ONBOARD) {
                        popUpTo(Routes.SPLASH) { inclusive = true }
                    } else navController.navigate(Routes.MAIN) {
                        popUpTo(Routes.SPLASH) { inclusive = true }
                    }
                }
            )
        }

//        // Onboarding
//        composable(Routes.ONBOARD) {
//            OnboardingScreen(
//                onFinished = {
//                    navController.navigate(Routes.MAIN) {
//                        popUpTo(Routes.ONBOARD) { inclusive = true }
//                    }
//                }
//            )
//        }

//        // Main with bottom nav
//        composable(Routes.MAIN) {
//            MainScreen()
//        }
    }
}

