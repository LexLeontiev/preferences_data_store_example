package com.example.myapplication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    /*
    минусы: нет типов Double и еще какого-то у SharedPrefs в отличие от DataStore
     */
}