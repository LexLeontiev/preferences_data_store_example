package com.lexleontiev.preferences_data_store_example.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.preference.PreferenceDataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


class UserPreferencesDataStore(
    private val dataStore: DataStore<Preferences>,
    private val coroutineScope: CoroutineScope
): PreferenceDataStore() {

    override fun putString(key: String?, value: String?) {
        if (key != null) {
            coroutineScope.launch {
                dataStore.edit { preferences ->
                    if (value != null) preferences[stringPreferencesKey(key)] = value
                    else preferences.remove(stringPreferencesKey(key))
                }
            }
        }
    }

    override fun putStringSet(key: String?, values: Set<String>?) {
        if (key != null) {
            coroutineScope.launch {
                dataStore.edit { preferences ->
                    if (values != null) preferences[stringSetPreferencesKey(key)] = values
                    else preferences.remove(stringSetPreferencesKey(key))
                }
            }
        }
    }

    override fun putInt(key: String?, value: Int) {
        if (key != null) {
            coroutineScope.launch {
                dataStore.edit { preferences ->
                    preferences[intPreferencesKey(key)] = value
                }
            }
        }
    }

    override fun putLong(key: String?, value: Long) {
        if (key != null) {
            coroutineScope.launch {
                dataStore.edit { preferences ->
                    preferences[longPreferencesKey(key)] = value
                }
            }
        }
    }

    override fun putFloat(key: String?, value: Float) {
        if (key != null) {
            coroutineScope.launch {
                dataStore.edit { preferences ->
                    preferences[floatPreferencesKey(key)] = value
                }
            }
        }
    }

    override fun putBoolean(key: String?, value: Boolean) {
        if (key != null) {
            coroutineScope.launch {
                dataStore.edit { preferences ->
                    preferences[booleanPreferencesKey(key)] = value
                }
            }
        }
    }

    override fun getString(key: String?, defValue: String?): String? = key?.let {
        runBlocking { dataStore.data.first()[stringPreferencesKey(it)] ?: defValue }
    } ?: defValue

    override fun getStringSet(key: String?, defValues: Set<String>?): Set<String>? = key?.let {
        runBlocking { dataStore.data.first()[stringSetPreferencesKey(it)] }
    } ?: defValues

    override fun getInt(key: String?, defValue: Int): Int = key?.let {
        runBlocking { dataStore.data.first()[intPreferencesKey(it)] }
    } ?: defValue

    override fun getLong(key: String?, defValue: Long): Long = key?.let {
        runBlocking { dataStore.data.first()[longPreferencesKey(it)] }
    } ?: defValue

    override fun getFloat(key: String?, defValue: Float): Float = key?.let {
        runBlocking { dataStore.data.first()[floatPreferencesKey(it)] }
    } ?: defValue

    override fun getBoolean(key: String?, defValue: Boolean): Boolean = key?.let {
        runBlocking { dataStore.data.first()[booleanPreferencesKey(it)] }
    } ?: defValue
}