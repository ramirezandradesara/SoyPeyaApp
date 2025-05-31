package com.soyhenry.feature.login

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {
    var username by mutableStateOf("")
    var password by mutableStateOf("")
    var loginSuccess by mutableStateOf<Boolean?>(null)

    private val correctUser = "admin"
    private val correctPass = "123"

    fun onLoginClick() {
        loginSuccess = username == correctUser && password == correctPass
    }

    fun resetLoginState() {
        loginSuccess = null
    }
}