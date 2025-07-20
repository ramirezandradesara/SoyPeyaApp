package com.soyhenry.core2.session

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import androidx.datastore.preferences.preferencesDataStore
import com.soyhenry.core2.model.domain.User

private val Context.dataStore by preferencesDataStore(name = "user_prefs")

class UserPreferences(private val context: Context) {

    companion object {
        private val ID_KEY = stringPreferencesKey("user_id")
        private val FULLNAME_KEY = stringPreferencesKey("user_fullname")
        private val EMAIL_KEY = stringPreferencesKey("user_email")
        private val IMAGE_KEY = stringPreferencesKey("user_image")
        private val ENCRYPTED_PASSWORD_KEY = stringPreferencesKey("user_encrypted_password")
    }

    val user: Flow<User?> = context.dataStore.data.map { prefs ->
        val id = prefs[ID_KEY]
        val name = prefs[FULLNAME_KEY]
        val email = prefs[EMAIL_KEY]
        val image = prefs[IMAGE_KEY]
        val encryptedPassword = prefs[ENCRYPTED_PASSWORD_KEY]

        if (id != null && name != null && email != null && encryptedPassword != null) {
            User(
                id = id,
                fullName = name,
                email = email,
                imageUrl = image ?: "",
                encryptedPassword = encryptedPassword
            )
        } else null
    }

    suspend fun saveUser(user: User) {
        context.dataStore.edit { prefs ->
            prefs[ID_KEY] = user.id
            prefs[FULLNAME_KEY] = user.fullName
            prefs[EMAIL_KEY] = user.email
            prefs[IMAGE_KEY] = user.imageUrl
            prefs[ENCRYPTED_PASSWORD_KEY] = user.encryptedPassword
        }
    }

    suspend fun logout() {
        context.dataStore.edit { it.clear() }
    }
}

