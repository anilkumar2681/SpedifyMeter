package com.team42.spedifymeter.data.preferences

import com.team42.spedifymeter.data.DataStoreManager
import kotlinx.coroutines.flow.Flow

/**
 * Project: Spedify Meter
 * File: Prefs.kt
 * Created By: ANIL KUMAR on 9/9/2025
 * Copyright © 2025 Team42. All rights reserved.
 **/
class Prefs(private val ds: DataStoreManager) {

    // ---- Exposed Flows ----
    val onboardingDone: Flow<Boolean> = ds.getPreference(PrefsKeys.OnboardingDone, false)
    val darkTheme: Flow<Boolean> = ds.getPreference(PrefsKeys.DarkTheme, true)
    val personalizedAds: Flow<Boolean> = ds.getPreference(PrefsKeys.PersonalizedAds, true)
    val adFrequency: Flow<String> =
        ds.getPreference(PrefsKeys.AdFrequency, PrefsDefaults.AD_FREQUENCY)
    val privacyPolicyAccepted: Flow<Boolean> =
        ds.getPreference(PrefsKeys.PrivacyPolicyAccepted, false)
    val dataUsageAccepted: Flow<Boolean> = ds.getPreference(PrefsKeys.DataUsageAccepted, false)

    // ---- Exposed Setters ----
    suspend fun setOnboardingDone(value: Boolean) =
        ds.setPreference(PrefsKeys.OnboardingDone, value)

    suspend fun setDarkTheme(value: Boolean) = ds.setPreference(PrefsKeys.DarkTheme, value)
    suspend fun setPersonalizedAds(value: Boolean) =
        ds.setPreference(PrefsKeys.PersonalizedAds, value)

    suspend fun setAdFrequency(value: String) = ds.setPreference(PrefsKeys.AdFrequency, value)
    suspend fun setPrivacyPolicyAccepted(value: Boolean) =
        ds.setPreference(PrefsKeys.PrivacyPolicyAccepted, value)

    suspend fun setDataUsageAccepted(value: Boolean) =
        ds.setPreference(PrefsKeys.DataUsageAccepted, value)

    // ---- Exposed Updaters ----
    suspend fun toggleDarkTheme() =
        ds.updatePreference(PrefsKeys.DarkTheme, true) { !it }

    suspend fun togglePersonalizedAds() =
        ds.updatePreference(PrefsKeys.PersonalizedAds, true) { !it }

    // ---- Exposed Clear ----
    suspend fun clearAll() = ds.clearAll()
}