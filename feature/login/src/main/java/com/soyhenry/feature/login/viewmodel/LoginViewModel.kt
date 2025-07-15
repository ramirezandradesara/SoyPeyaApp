package com.soyhenry.feature.login.viewmodel

import android.content.Context
import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.soyhenry.core.session.UserPreferences
import com.soyhenry.feature.login.domain.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import retrofit2.HttpException
import java.io.IOException

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val userPreferences: UserPreferences
) : ViewModel() {
    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email.asStateFlow()

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password.asStateFlow()

    private val _loginSuccess = MutableStateFlow<Boolean?>(null)
    val loginSuccess: StateFlow<Boolean?> = _loginSuccess.asStateFlow()

    private val _toastMessage = MutableStateFlow<String?>(null)
    val toastMessage: StateFlow<String?> = _toastMessage.asStateFlow()

    fun onEmailChange(newEmail: String) {
        _email.value = newEmail
    }

    fun onPasswordChange(newPassword: String) {
        _password.value = newPassword
    }

    fun onLoginClick(context: Context) {
        viewModelScope.launch {
            if (!isValidEmail(_email.value)) {
                _toastMessage.value = "Invalid email format ❌"
                return@launch
            }

            try {
                loginUseCase(_email.value, _password.value)

                userPreferences.saveUserEmail(_email.value)
                _loginSuccess.value = true
                _toastMessage.value = "Welcome 🎉"
            } catch (e: HttpException) {
                val message = when (e.code()) {
                    401 -> "Invalid credentials"
                    404 -> "User not found"
                    500 -> "Server error. Please try again later"
                    else -> "Login error: ${e.code()}"
                }
                _toastMessage.value = message
            } catch (e: IOException) {
                _toastMessage.value = "Network error: please check your connection"
            } catch (e: Exception) {
                _toastMessage.value = "Unexpected error: ${e.message}"
            }
        }
    }

    fun clearToastMessage() {
        _toastMessage.value = null
    }

    private fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}