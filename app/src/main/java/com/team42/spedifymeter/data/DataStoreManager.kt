package com.team42.spedifymeter.data

import androidx.datastore.preferences.preferencesDataStore
import android.content.Context
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

/**
 * Project: Spedify Meter
 * File: DataStoreManager.kt
 * Created By: ANIL KUMAR on 9/9/2025
 * Copyright © 2025 Team42. All rights reserved.
 **/
private val Context.dataStore by preferencesDataStore(name = "app_prefs")

class DataStoreManager(private val context: Context) {

    // Generic getter with default + error handling
    fun <T> getPreference(
        key: Preferences.Key<T>,
        defaultValue: T
    ): Flow<T> = context.dataStore.data
        .catch { exception ->
            if (exception is IOException) emit(emptyPreferences())
            else throw exception
        }
        .map { prefs -> prefs[key] ?: defaultValue }

    // Generic setter
    suspend fun <T> setPreference(
        key: Preferences.Key<T>,
        value: T
    ): Result<Unit> = runCatching {
        context.dataStore.edit { prefs -> prefs[key] = value }
    }

    // Generic updater (transform value)
    suspend fun <T> updatePreference(
        key: Preferences.Key<T>,
        defaultValue: T,
        transform: (T) -> T
    ): Result<Unit> = runCatching {
        context.dataStore.edit { prefs ->
            val oldValue = prefs[key] ?: defaultValue
            prefs[key] = transform(oldValue)
        }
    }

    // Clear all preferences
    suspend fun clearAll(): Result<Unit> = runCatching {
        context.dataStore.edit { it.clear() }
    }
}