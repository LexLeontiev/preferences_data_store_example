package com.lexleontiev.preferences_data_store_example.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lexleontiev.preferences_data_store_example.data.UserPreferencesRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ObservePreferencesViewModel @Inject constructor(
    private val userPreferencesRepo: UserPreferencesRepo
) : ViewModel() {

    private val _uiState = MutableStateFlow(UserPreferences.empty())
    val uiState: StateFlow<UserPreferences> = _uiState

    init {
        viewModelScope.launch {
            userPreferencesRepo.userPreferencesFlow
                .collect { userPreferences ->
                    _uiState.value = userPreferences
                }
        }
    }
}