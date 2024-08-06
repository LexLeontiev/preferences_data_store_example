package com.lexleontiev.preferences_data_store_example.presentation

import android.os.Bundle
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.lifecycleScope
import androidx.preference.ListPreference
import androidx.preference.MultiSelectListPreference
import androidx.preference.PreferenceCategory
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import com.lexleontiev.preferences_data_store_example.data.PreferenceKey
import com.lexleontiev.preferences_data_store_example.data.UserPreferencesDataStore
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class UserPreferencesFragment : PreferenceFragmentCompat() {

    @Inject lateinit var dataStore: DataStore<Preferences>

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        val context = preferenceManager.context
        val screen = preferenceManager.createPreferenceScreen(context)

        preferenceManager.preferenceDataStore = UserPreferencesDataStore(
            dataStore = dataStore,
            coroutineScope = lifecycleScope
        )

        // BOOLEAN
        val booleanSection = PreferenceCategory(context).apply {
            key = PreferenceKey.KEY_BOOLEAN_SECTION
            title = "Boolean section"
            isIconSpaceReserved = false
        }
        screen.addPreference(booleanSection)
        booleanSection.addPreference(
            SwitchPreference(context).apply {
                key = PreferenceKey.KEY_BOOLEAN_PREFERENCE
                title = "Select boolean"
                isIconSpaceReserved = false
            }
        )
        // INT
        val intSection = PreferenceCategory(context).apply {
            key = PreferenceKey.KEY_INT_SECTION
            title = "Integer section"
            isIconSpaceReserved = false
        }
        screen.addPreference(intSection)
        val intDefault = 1
        val intData: Map<String, Int> = mapOf(
            "1" to intDefault,
            "2" to 2,
            "3" to 3,
        )
        intSection.addPreference(
            IntListPreference(context).apply {
                key = PreferenceKey.KEY_INT_PREFERENCE
                title = "Select integer"
                dialogTitle = "Select integer"
                entries = intData.keys.toTypedArray()
                setTypedValues(intData.values, intDefault)
                summaryProvider = ListPreference.SimpleSummaryProvider.getInstance()
                isIconSpaceReserved = false
            }
        )
        // LONG
        val longSection = PreferenceCategory(context).apply {
            key = PreferenceKey.KEY_LONG_SECTION
            title = "Long section"
            isIconSpaceReserved = false
        }
        screen.addPreference(longSection)
        val longDefault = 1_000_000_000_000_000L
        val longData: Map<String, Long> = mapOf(
            "1 quadrillion" to longDefault,
            "2 quadrillions" to 2_000_000_000_000_000L,
            "3 quadrillions" to 3_000_000_000_000_000L,
        )
        longSection.addPreference(
            LongListPreference(context).apply {
                key = PreferenceKey.KEY_LONG_PREFERENCE
                title = "Select long"
                dialogTitle = "Select long"
                entries = longData.keys.toTypedArray()
                setTypedValues(longData.values, longDefault)
                summaryProvider = ListPreference.SimpleSummaryProvider.getInstance()
                isIconSpaceReserved = false
            }
        )
        // FLOAT
        val floatSection = PreferenceCategory(context).apply {
            key = PreferenceKey.KEY_FLOAT_SECTION
            title = "Float section"
            isIconSpaceReserved = false
        }
        screen.addPreference(floatSection)
        val floatDefault = 1.2f
        val floatData: Map<String, Float> = mapOf(
            "1.2" to floatDefault,
            "2.456" to 2.456f,
            "-12.8954" to -12.8954f,
        )
        floatSection.addPreference(
            FloatListPreference(context).apply {
                key = PreferenceKey.KEY_FLOAT_PREFERENCE
                title = "Select float"
                dialogTitle = "Select float"
                entries = floatData.keys.toTypedArray()
                setTypedValues(floatData.values, floatDefault)
                summaryProvider = ListPreference.SimpleSummaryProvider.getInstance()
                isIconSpaceReserved = false
            }
        )
        // STRING
        val stringSection = PreferenceCategory(context).apply {
            key = PreferenceKey.KEY_STRING_SECTION
            title = "String section"
            isIconSpaceReserved = false
        }
        screen.addPreference(stringSection)
        val stringDefault = "some_string"
        val stringData = mapOf(
            "Some string" to stringDefault,
            "Another string" to "another_string",
            "Completely different string" to "completely_different_string"
        )
        stringSection.addPreference(
            ListPreference(context).apply {
                key = PreferenceKey.KEY_STRING_PREFERENCE
                title = "Select string"
                dialogTitle = "Select string"
                entries = stringData.keys.toTypedArray()
                entryValues = stringData.values.toTypedArray()
                setDefaultValue(stringDefault)
                summaryProvider = ListPreference.SimpleSummaryProvider.getInstance()
                isIconSpaceReserved = false
            }
        )
        // STRING SET
        val stringSetSection = PreferenceCategory(context).apply {
            key = PreferenceKey.KEY_STRING_SET_SECTION
            title = "String set section"
            isIconSpaceReserved = false
        }
        screen.addPreference(stringSetSection)
        stringSetSection.addPreference(
            MultiSelectListPreference(context).apply {
                key = PreferenceKey.KEY_STRING_SET_PREFERENCE
                title = "Select string set"
                dialogTitle = "Select string set"
                entries = stringData.keys.toTypedArray()
                entryValues = stringData.values.toTypedArray()
                setDefaultValue(emptySet<String>())
                isIconSpaceReserved = false
            }
        )
        preferenceScreen = screen
    }
}
