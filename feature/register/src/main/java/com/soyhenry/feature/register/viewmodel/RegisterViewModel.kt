package com.soyhenry.feature.register.viewmodel

import android.content.Context
import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {
    private val _email = MutableStateFlow("")
    val email = _email.asStateFlow()

    private val _name = MutableStateFlow("")
    val name = _name.asStateFlow()

    private val _password = MutableStateFlow("")
    val password = _password.asStateFlow()

    private val _confirmPassword = MutableStateFlow("")
    val confirmPassword = _confirmPassword.asStateFlow()


    private val _emailError = MutableStateFlow<String?>(null)
    val emailError = _emailError.asStateFlow()

    private val _nameError = MutableStateFlow<String?>(null)
    val nameError = _nameError.asStateFlow()

    private val _passwordError = MutableStateFlow<String?>(null)
    val passwordError = _passwordError.asStateFlow()

    private val _confirmPasswordError = MutableStateFlow<String?>(null)
    val confirmPasswordError = _confirmPasswordError.asStateFlow()


    private val _registerSuccess = MutableStateFlow(false)
    val registerSuccess = _registerSuccess.asStateFlow()

    private val _toastMessage = MutableStateFlow<String?>(null)
    val toastMessage = _toastMessage.asStateFlow()

    fun onEmailChange(value: String) {
        _email.value = value
        validateEmail()
    }

    fun onNameChange(value: String) {
        _name.value = value
        validateName()
    }

    fun onPasswordChange(value: String) {
        _password.value = value
        validatePassword()
        validateConfirmPassword()
    }

    fun onConfirmPasswordChange(value: String) {
        _confirmPassword.value = value
        validateConfirmPassword()
    }

    private fun validateEmail(): Boolean {
        return when {
            _email.value.isEmpty() -> {
                _emailError.value = "Email is required"
                false
            }
            !Patterns.EMAIL_ADDRESS.matcher(_email.value).matches() -> {
                _emailError.value = "Invalid email format"
                false
            }
            else -> {
                _emailError.value = null
                true
            }
        }
    }

    private fun validateName(): Boolean {
        return if (_name.value.isEmpty()) {
            _nameError.value = "Name is required"
            false
        } else {
            _nameError.value = null
            true
        }
    }

    private fun validatePassword(): Boolean {
        return when {
            _password.value.isEmpty() -> {
                _passwordError.value = "Password is required"
                false
            }
            _password.value.length < 8 -> {
                _passwordError.value = "Password must be at least 8 characters"
                false
            }
            else -> {
                _passwordError.value = null
                true
            }
        }
    }

    private fun validateConfirmPassword(): Boolean {
        return when {
            _confirmPassword.value.isEmpty() -> {
                _confirmPasswordError.value = "Please confirm your password"
                false
            }
            _confirmPassword.value != _password.value -> {
                _confirmPasswordError.value = "Passwords don't match"
                false
            }
            else -> {
                _confirmPasswordError.value = null
                true
            }
        }
    }

    fun onRegisterClick(context: Context) {
        val isEmailValid = validateEmail()
        val isNameValid = validateName()
        val isPasswordValid = validatePassword()
        val isConfirmPasswordValid = validateConfirmPassword()

        if (isEmailValid && isNameValid && isPasswordValid && isConfirmPasswordValid) {
            context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE).edit().apply {
                putString("registered_email", _email.value)
                putString("registered_name", _name.value)
                putString("registered_password", _password.value)
                putBoolean("is_registered", true)
                apply()
            }
            viewModelScope.launch {
                _toastMessage.value = "Registration successful"
                _registerSuccess.value = true
            }
        } else {
            viewModelScope.launch {
                _toastMessage.value = "Please fix the errors in the form"
            }
        }
    }

    fun clearToastMessage() {
        _toastMessage.value = null
    }
}