package com.team42.spedifymeter.data.preferences

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

/**
 * Project: Spedify Meter
 * File: PrefsKeys.kt
 * Created By: ANIL KUMAR on 9/9/2025
 * Copyright © 2025 Team42. All rights reserved.
 **/
object PrefsKeys {
    val OnboardingDone = booleanPreferencesKey("onboarding_done")
    val DarkTheme = booleanPreferencesKey("dark_theme")
    val PersonalizedAds = booleanPreferencesKey("personalized_ads")
    val AdFrequency = stringPreferencesKey("ad_frequency")
    val PrivacyPolicyAccepted = booleanPreferencesKey("privacy_policy_accepted")
    val DataUsageAccepted = booleanPreferencesKey("data_usage_accepted")
}