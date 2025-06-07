package com.soyhenry.feature.register

import android.content.Context
import android.util.Patterns
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class RegisterViewModel : ViewModel() {

    private val _email = MutableStateFlow("")
    val email = _email.asStateFlow()

    private val _name = MutableStateFlow("")
    val name = _name.asStateFlow()

    private val _password = MutableStateFlow("")
    val password = _password.asStateFlow()

    private val _confirmPassword = MutableStateFlow("")
    val confirmPassword = _confirmPassword.asStateFlow()

    private val _registerSuccess = MutableStateFlow(false)
    val registerSuccess = _registerSuccess.asStateFlow()

    private val _toastMessage = MutableStateFlow<String?>(null)
    val toastMessage = _toastMessage.asStateFlow()

    fun onEmailChange(value: String) { _email.value = value }
    fun onNameChange(value: String) { _name.value = value }
    fun onPasswordChange(value: String) { _password.value = value }
    fun onConfirmPasswordChange(value: String) { _confirmPassword.value = value }

    fun onRegisterClick(context: Context) {
        val isEmailValid = Patterns.EMAIL_ADDRESS.matcher(email.value).matches()
        val arePasswordsMatching = password.value == confirmPassword.value
        val isNameValid = name.value.isNotBlank()

        when {
            !isEmailValid -> _toastMessage.value = "Email inválido"
            !arePasswordsMatching -> _toastMessage.value = "Las contraseñas no coinciden"
            !isNameValid -> _toastMessage.value = "Nombre requerido"
            else -> {
                context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE).edit().apply {
                    putString("registered_email", email.value)
                    putString("registered_name", name.value)
                    putString("registered_password", password.value)
                    putBoolean("is_registered", true)
                    apply()
                }
                _toastMessage.value = "Registro exitoso"
                _registerSuccess.value = true
            }
        }
    }

    fun clearToastMessage() {
        _toastMessage.value = null
    }
}
