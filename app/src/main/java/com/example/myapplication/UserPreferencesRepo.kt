package com.example.myapplication

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException


class UserPreferencesRepo(
    dataStore: DataStore<Preferences>,
) {

    val userPreferencesFlow: Flow<UserPreferences> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            val booleanPref = preferences[booleanPreferencesKey(PreferenceKey.KEY_BOOLEAN_PREFERENCE)] ?: false
            val intPref = preferences[intPreferencesKey(PreferenceKey.KEY_INT_PREFERENCE)] ?: 0
            val longPref = preferences[longPreferencesKey(PreferenceKey.KEY_LONG_PREFERENCE)] ?: 0L
            val floatPref = preferences[floatPreferencesKey(PreferenceKey.KEY_FLOAT_PREFERENCE)] ?: 0f
            val stringPref = preferences[stringPreferencesKey(PreferenceKey.KEY_STRING_PREFERENCE)] ?: ""
            val stringSetPref = preferences[stringSetPreferencesKey(PreferenceKey.KEY_STRING_SET_PREFERENCE)] ?: emptySet()
            UserPreferences(booleanPref, intPref, longPref, floatPref, stringPref, stringSetPref)
        }
}