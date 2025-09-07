package com.team42.spedifymeter.ui.splash

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import com.team42.spedifymeter.ui.theme.DarkGradient
import com.team42.spedifymeter.ui.theme.SpedifyMeterTheme
import com.team42.spedifymeter.ui.theme.Teal200
import kotlinx.coroutines.delay

/**
 * Project: Spedify Meter
 * File: SplashScreen.kt
 * Created By: ANIL KUMAR on 9/3/2025
 * Copyright © 2025 Team42. All rights reserved.
 **/
@Composable
fun SplashScreen(onNavigate: (Boolean) -> Unit) {
    var visible by remember { mutableStateOf(false) }
    val alpha by animateFloatAsState(targetValue = if (visible) 1f else 0f)
    val scale by animateFloatAsState(
        targetValue = if (visible) 1f else 0.85f,
        animationSpec = tween(800)
    )

    LaunchedEffect(Unit) {
        visible = true 
        delay(1600)
    }

    Surface(color = MaterialTheme.colorScheme.background) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(DarkGradient),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Speed Tester",
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .alpha(alpha)
                        .scale(scale),
                    color = Teal200
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Preparing environment…",
                    style = MaterialTheme.typography.labelLarge,
                    modifier = Modifier.alpha(alpha * 0.9f)
                )
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    SpedifyMeterTheme { SplashScreen(onNavigate = {}) }
}
