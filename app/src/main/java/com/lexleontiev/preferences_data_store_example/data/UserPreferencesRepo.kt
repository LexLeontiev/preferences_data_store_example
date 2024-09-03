package com.lexleontiev.preferences_data_store_example.data

import android.content.SharedPreferences
import com.lexleontiev.preferences_data_store_example.data.PreferenceKey.KEY_BOOLEAN_PREFERENCE
import com.lexleontiev.preferences_data_store_example.data.PreferenceKey.KEY_FLOAT_PREFERENCE
import com.lexleontiev.preferences_data_store_example.data.PreferenceKey.KEY_INT_PREFERENCE
import com.lexleontiev.preferences_data_store_example.data.PreferenceKey.KEY_LONG_PREFERENCE
import com.lexleontiev.preferences_data_store_example.data.PreferenceKey.KEY_STRING_PREFERENCE
import com.lexleontiev.preferences_data_store_example.data.PreferenceKey.KEY_STRING_SET_PREFERENCE
import com.lexleontiev.preferences_data_store_example.presentation.UserPreferences
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.catch
import java.io.IOException


class UserPreferencesRepo(
    sharedPreferences: SharedPreferences,
) {

    companion object {

        const val PREFS_NAME = "user_preferences"

        val keys = arrayOf(KEY_BOOLEAN_PREFERENCE, KEY_INT_PREFERENCE, KEY_LONG_PREFERENCE,
            KEY_FLOAT_PREFERENCE, KEY_STRING_PREFERENCE, KEY_STRING_SET_PREFERENCE)

        fun buildUserPreference(prefs: SharedPreferences): UserPreferences {
            val booleanPref = prefs.getBoolean(KEY_BOOLEAN_PREFERENCE, false)
            // Int, Long and Float are persisted as strings because we use build-in ListPreference
            // on Settings screen that transforms everything to strings
            val intPref = prefs.getString(KEY_INT_PREFERENCE, "")?.toIntOrNull() ?: 0
            val longPref = prefs.getString(KEY_LONG_PREFERENCE, "")?.toLongOrNull() ?: 0L
            val floatPref = prefs.getString(KEY_FLOAT_PREFERENCE, "")?.toFloatOrNull() ?: 0f
            val stringPref = prefs.getString(KEY_STRING_PREFERENCE, "") ?: ""
            val stringSetPref = prefs.getStringSet(KEY_STRING_SET_PREFERENCE, emptySet()) ?: emptySet()
            return UserPreferences(booleanPref, intPref, longPref, floatPref, stringPref, stringSetPref)
        }
    }

    val userPreferencesFlow: Flow<UserPreferences> = callbackFlow {
        val listener = SharedPreferences.OnSharedPreferenceChangeListener { prefs, key ->
            if (key in keys) {
                trySend(buildUserPreference(prefs))
            }
        }
        sharedPreferences.registerOnSharedPreferenceChangeListener(listener)
        // send initial value
        send(buildUserPreference(sharedPreferences))
        awaitClose {
            sharedPreferences.unregisterOnSharedPreferenceChangeListener(listener)
        }
    }.catch { exception ->
        if (exception is IOException) {
            emit(UserPreferences.empty())
        } else {
            throw exception
        }
    }
}

