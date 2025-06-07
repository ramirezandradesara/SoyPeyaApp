package com.soyhenry.feature.login.viewmodel

import android.content.Context
import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email.asStateFlow()

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password.asStateFlow()

    private val _loginSuccess = MutableStateFlow<Boolean?>(null)
    val loginSuccess: StateFlow<Boolean?> = _loginSuccess.asStateFlow()

    private val _toastMessage = MutableStateFlow<String?>(null)
    val toastMessage: StateFlow<String?> = _toastMessage.asStateFlow()

    private val _showRegisterSheet = MutableStateFlow(false)

    private val correctEmail = "admin@gmail.com"
    private val correctPass = "123"

    fun onEmailChange(newEmail: String) {
        _email.value = newEmail
    }

    fun onPasswordChange(newPassword: String) {
        _password.value = newPassword
    }

    fun handleShowRegisterSheet() {
        _showRegisterSheet.value = !_showRegisterSheet.value
    }

    fun onLoginClick(context: Context) {
        viewModelScope.launch {
            if (!isValidEmail(_email.value)) {
                _toastMessage.value = "Invalid email format ❌"
                return@launch
            }

            val success = _email.value == correctEmail && _password.value == correctPass
            _loginSuccess.value = success

            if (success) {
                saveLoginStatus(context, true)
                _toastMessage.value = "Welcome 🎉"
            } else {
                _toastMessage.value = "Wrong user or password ❌"
            }
        }
    }

    private fun saveLoginStatus(context: Context, isLoggedIn: Boolean) {
        val sharedPreferences = context.getSharedPreferences("login_prefs", Context.MODE_PRIVATE)
        sharedPreferences.edit().putBoolean("is_logged_in", isLoggedIn).apply()
    }

    fun clearToastMessage() {
        _toastMessage.value = null
    }

    private fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}