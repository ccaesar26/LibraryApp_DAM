package com.example.libraryapp_lab4.data.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserPreferencesRepository @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {

    // Companion object pentru a defini cheile într-un mod sigur și centralizat.
    private companion object {
        val USER_NAME = stringPreferencesKey("user_name")
    }

    /**
     * Expune un Flow care emite numele utilizatorului de fiecare dată când acesta se schimbă.
     * Acesta este fluxul reactiv pe care ViewModel-ul îl va observa.
     */
    val userNameFlow: Flow<String> = dataStore.data
        .map { preferences ->
            // Citim valoarea de la cheia USER_NAME.
            // Dacă nu există, returnăm un string gol.
            preferences[USER_NAME] ?: ""
        }

    /**
     * Funcție suspendată pentru a salva un nou nume de utilizator.
     * Operația se execută în siguranță pe un fir de execuție de fundal.
     *
     * @param name Numele de salvat.
     */
    suspend fun saveUserName(name: String) {
        dataStore.edit { preferences ->
            preferences[USER_NAME] = name
        }
    }
}