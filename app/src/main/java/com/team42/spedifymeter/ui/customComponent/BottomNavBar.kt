package com.team42.spedifymeter.ui.customComponent

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.team42.spedifymeter.ui.theme.DarkColor
import com.team42.spedifymeter.ui.theme.Teal200

/**
 * Project: Spedify Meter
 * File: BottomNavBar.kt
 * Created By: ANIL KUMAR on 9/5/2025
 * Copyright © 2025 Team42. All rights reserved.
 **/
@Composable
fun BottomNavBar(
    modifier: Modifier = Modifier,
    selectedTab: Int,
    onTabSelected: (Int) -> Unit
) {
    NavigationBar(
        modifier = modifier,
        tonalElevation = 8.dp,
        containerColor = DarkColor,
        contentColor = Teal200
    ) {
        NavigationBarItem(
            selected = selectedTab == 0,
            onClick = { onTabSelected(0) },
            icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
            label = { Text("Test Speed") },
            colors = NavigationBarItemDefaults.colors(
                selectedTextColor = Teal200,
                selectedIconColor = Teal200,
                indicatorColor = DarkColor,
                unselectedTextColor = Color.White,
                unselectedIconColor = Color.White,
            )
        )
        NavigationBarItem(
            selected = selectedTab == 1,
            onClick = { onTabSelected(1) },
            icon = { Icon(Icons.Default.Settings, contentDescription = "Settings") },
            label = { Text("Status") },
            colors = NavigationBarItemDefaults.colors(
                selectedTextColor = Teal200,
                selectedIconColor = Teal200,
                indicatorColor = DarkColor,
                unselectedTextColor = Color.White,
                unselectedIconColor = Color.White,
            )
        )
        NavigationBarItem(
            selected = selectedTab == 2,
            onClick = { onTabSelected(2) },
            icon = { Icon(Icons.Default.Settings, contentDescription = "Settings") },
            label = { Text("Settings") },
            colors = NavigationBarItemDefaults.colors(
                selectedTextColor = Teal200,
                selectedIconColor = Teal200,
                indicatorColor = DarkColor,
                unselectedTextColor = Color.White,
                unselectedIconColor = Color.White,
            )
        )
    }
}
