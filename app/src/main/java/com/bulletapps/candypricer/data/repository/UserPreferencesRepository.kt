package com.bulletapps.candypricer.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.bulletapps.candypricer.data.response.LoginResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserPreferencesRepository @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {

    private object PreferencesKeys {
        val TOKEN_KEY = stringPreferencesKey("token_key")
        val EMAIL_KEY = stringPreferencesKey("email_key")
    }

    val userPreferencesFlow: Flow<LoginResponse> = dataStore.data.map { preferences ->
        val email = preferences[PreferencesKeys.EMAIL_KEY].orEmpty()
        val token = preferences[PreferencesKeys.TOKEN_KEY].orEmpty()
        LoginResponse(email, token)
    }
}