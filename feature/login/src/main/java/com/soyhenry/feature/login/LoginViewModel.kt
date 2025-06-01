package com.soyhenry.feature.login

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email.asStateFlow()

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password.asStateFlow()

    private val _loginSuccess = MutableStateFlow<Boolean?>(null)
    val loginSuccess: StateFlow<Boolean?> = _loginSuccess.asStateFlow()


    private val correctEmail = "admin@gmail.com"
    private val correctPass = "123"

    fun onEmailChange(newEmail: String) {
        _email.value = newEmail
    }

    fun onPasswordChange(newPassword: String) {
        _password.value = newPassword
    }

    fun onLoginClick() {
        viewModelScope.launch {
            _loginSuccess.value = (_email.value == correctEmail && _password.value == correctPass)
        }
    }

    fun resetLoginState() {
        _loginSuccess.value = null
    }
}
