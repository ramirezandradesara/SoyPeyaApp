package com.soyhenry.feature.login.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.soyhenry.core.session.UserPreferences
import com.soyhenry.feature.login.domain.usecase.LoginUseCase
import com.soyhenry.library.utils.validator.EmailValidator
import com.soyhenry.library.utils.validator.PasswordValidator
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

    private val _emailError = MutableStateFlow<String?>(null)
    val emailError: StateFlow<String?> = _emailError.asStateFlow()

    private val _passwordError = MutableStateFlow<String?>(null)
    val passwordError: StateFlow<String?> = _passwordError.asStateFlow()

    private val _loginSuccess = MutableStateFlow<Boolean?>(false)
    val loginSuccess: StateFlow<Boolean?> = _loginSuccess.asStateFlow()

    private val _toastMessage = MutableStateFlow<String?>(null)
    val toastMessage: StateFlow<String?> = _toastMessage.asStateFlow()

    fun onEmailChange(newEmail: String) {
        _email.value = newEmail
    }

    fun onPasswordChange(newPassword: String) {
        _password.value = newPassword
    }

    private fun validateEmail(): Boolean {
        val result = EmailValidator.validate(_email.value)
        _emailError.value = result.errorMessage
        return result.isValid
    }

    private fun validatePassword(): Boolean {
        val result = PasswordValidator.validate(_password.value)
        _passwordError.value = result.errorMessage
        return result.isValid
    }

    fun onLoginClick() {
        val isEmailValid = validateEmail()
        val isPasswordValid = validatePassword()

        if (!isEmailValid || !isPasswordValid) {
            _toastMessage.value = "Se encontraron errores en el formulario"
            return
        }

        viewModelScope.launch {
            try {
                val response = loginUseCase(_email.value, _password.value)
                userPreferences.saveUser(response.user)
                _loginSuccess.value = true
                _toastMessage.value = response.message
            } catch (e: HttpException) {
                val message = when (e.code()) {
                    401 -> "Credenciales inválidas"
                    404 -> "Usuario no encontrado"
                    500 -> "Error del servidor. Por favor, inténtalo de nuevo más tarde"
                    else -> "Error: ${e.code()}"
                }
                _toastMessage.value = message
            } catch (e: IOException) {
                _toastMessage.value = "Error de red. Verifica tu conexión"
            } catch (e: Exception) {
                _toastMessage.value = "Error inesperado: ${e.message}"
            }
        }
    }

    fun clearToastMessage() {
        _toastMessage.value = null
    }
}