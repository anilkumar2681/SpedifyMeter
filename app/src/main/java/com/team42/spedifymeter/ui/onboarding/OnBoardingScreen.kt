package com.team42.spedifymeter.ui.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.team42.spedifymeter.R
import com.team42.spedifymeter.ui.theme.DarkGradient
import com.team42.spedifymeter.ui.theme.Teal200


/**
 * Project: Spedify Meter
 * File: OnBoardingScreen.kt
 * Created By: ANIL KUMAR on 9/3/2025
 * Copyright © 2025 Team42. All rights reserved.
 **/
@Composable
fun OnBoardingScreen(
    onFinished: () -> Unit
) {
    val page = remember { mutableIntStateOf(0) }

    val subTitle = listOf(
        "Welcome to SPEDIFY METER – your trusted companion to measure internet speed with accuracy and ease.",
        "Track your download, upload, and latency in real time to understand your connection better.",
        "Enjoy a simple, intuitive, and modern interface designed to make speed testing effortless.",
        "Get started today and take control of your internet performance with detailed insights."
    )
    val pages = listOf(
        "Welcome guys!",
        "Track internet speed",
        "Easy to use",
        "Get started"
    )

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkGradient)
            .padding(WindowInsets.safeDrawing.asPaddingValues())
    ) {

        val isLandscape = this.maxWidth > this.maxHeight
        val imageHeight = if (isLandscape) maxHeight * 0.25f else maxHeight * 0.32f

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = pages[page.intValue],
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Image(
                painter = painterResource(
                    id = when (page.intValue) {
                        0 -> R.drawable.welcome
                        1 -> R.drawable.track_speed
                        2 -> R.drawable.easy_to_use
                        else -> R.drawable.get_started
                    }
                ),
                contentDescription = null,
                modifier = Modifier
                    .height(imageHeight)
                    .fillMaxWidth()
                    .padding(16.dp),
                contentScale = ContentScale.Fit
            )

            Text(
                text = subTitle[page.intValue],
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            // Page indicators
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                pages.forEachIndexed { index, _ ->
                    val active = index == page.intValue
                    Box(
                        modifier = Modifier
                            .padding(4.dp)
                            .size(if (active) 12.dp else 8.dp)
                            .clip(CircleShape)
                            .background(if (active) Teal200 else Color.White.copy(alpha = 0.3f))
                    )
                }
            }

            // Bottom buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextButton(onClick = { onFinished()}) { Text("Skip") }
                Button(
                    onClick = {
                        if (page.intValue < pages.lastIndex) {
                            page.intValue += 1
                        } else {
                            onFinished()
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Black,
                        contentColor = Teal200
                    )
                ) {
                    Text(if (page.intValue < pages.lastIndex) "Next" else "Get Started")
                }
            }
        }
    }
}

