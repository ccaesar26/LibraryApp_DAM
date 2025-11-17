package com.example.libraryapp_lab4.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.libraryapp_lab4.data.preferences.UserPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    /**
     * Expunem numele utilizatorului ca un StateFlow.
     * `stateIn` convertește un Flow rece (de la DataStore) într-un Flow fierbinte (StateFlow),
     * care poate fi observat în siguranță de către UI.
     */
    val userName: StateFlow<String> = userPreferencesRepository.userNameFlow
        .stateIn(
            scope = viewModelScope,
            // Începe colectarea când UI-ul devine vizibil și se oprește după 5 secunde de inactivitate.
            started = SharingStarted.WhileSubscribed(5000L),
            // Valoarea inițială până când DataStore emite prima valoare.
            initialValue = ""
        )

    /**
     * Salvează numele introdus de utilizator.
     * @param name Noul nume de salvat.
     */
    fun saveUserName(name: String) {
        viewModelScope.launch {
            userPreferencesRepository.saveUserName(name)
        }
    }
}