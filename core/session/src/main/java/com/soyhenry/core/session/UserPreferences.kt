package com.soyhenry.core.session

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import androidx.datastore.preferences.preferencesDataStore

private val Context.dataStore by preferencesDataStore(name = "user_prefs")

class UserPreferences(private val context: Context) {

    companion object {
        private val EMAIL_KEY = stringPreferencesKey("user_email")
    }

    val userEmail: Flow<String?> = context.dataStore.data.map { prefs ->
        prefs[EMAIL_KEY]
    }

    suspend fun saveUserEmail(email: String) {
        context.dataStore.edit { prefs ->
            prefs[EMAIL_KEY] = email
        }
    }

    suspend fun logout() {
        context.dataStore.edit { prefs ->
            prefs.remove(EMAIL_KEY)
        }
    }
}
