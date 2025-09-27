package com.team42.spedifymeter.data

import android.content.Context
import android.util.Log
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
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

    fun <T> getPreference(
        key: Preferences.Key<T>,
        defaultValue: T
    ): Flow<T> = context.dataStore.data
        .catch { exception ->
            if (exception is IOException) emit(emptyPreferences())
            else throw exception
        }
        .map { prefs ->
            val value = prefs[key] ?: defaultValue
            Log.d("DataStoreManager", "Read ${key.name} = $value")
            value
        }

    suspend fun <T> setPreference(
        key: Preferences.Key<T>,
        value: T
    ): Result<Unit> = runCatching {
        context.dataStore.edit { prefs ->
            prefs[key] = value
            Log.d("DataStoreManager", "Saved ${key.name} = $value")
        }
    }

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