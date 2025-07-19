package com.soyhenry.feature.register.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.soyhenry.core.session.UserPreferences
import com.soyhenry.feature.register.domain.usecase.RegisterUseCase
import com.soyhenry.library.utils.validator.ConfirmPasswordValidator
import com.soyhenry.library.utils.validator.EmailValidator
import com.soyhenry.library.utils.validator.NameValidator
import com.soyhenry.library.utils.validator.PasswordValidator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import retrofit2.HttpException
import java.io.IOException

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase,
    private val userPreferences: UserPreferences,
) : ViewModel() {
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
        val result = EmailValidator.validate(_email.value)
        _emailError.value = result.errorMessage
        return result.isValid
    }

    private fun validateName(): Boolean {
        val result = NameValidator.validate(_name.value)
        _nameError.value = result.errorMessage
        return result.isValid
    }

    private fun validatePassword(): Boolean {
        val result = PasswordValidator.validate(_password.value)
        _passwordError.value = result.errorMessage
        return result.isValid
    }

    private fun validateConfirmPassword(): Boolean {
        val result = ConfirmPasswordValidator.validate(_password.value, _confirmPassword.value)
        _confirmPasswordError.value = result.errorMessage
        return result.isValid
    }

    fun onRegisterClick() {
        val isEmailValid = validateEmail()
        val isNameValid = validateName()
        val isPasswordValid = validatePassword()
        val isConfirmPasswordValid = validateConfirmPassword()

        if (isEmailValid && isNameValid && isPasswordValid && isConfirmPasswordValid) {
            viewModelScope.launch {
                try {
                    val response = registerUseCase(
                        email = _email.value,
                        name = _name.value,
                        password = _password.value
                    )
                    userPreferences.saveUser(response)
                    _toastMessage.value = "Registro exitoso"
                    _registerSuccess.value = true
                } catch (e: HttpException) {
                    val message = when (e.code()) {
                        400 -> "Error en la solicitud: por favor, verifique sus datos"
                        403 -> "Prohibido: no tienes permiso"
                        409 -> "El email ya está registrado"
                        500 -> "Error del servidor. Por favor, inténtalo de nuevo más tarde"
                        else -> "Error desconocido: ${e.code()}"
                    }
                    _toastMessage.value = "Error inesperado: $message"
                } catch (e: IOException) {
                    _toastMessage.value = "Error de red. Verifica tu conexión"
                } catch (e: Exception) {
                    _toastMessage.value = "Error inesperado: ${e.message}"
                }
            }
        } else {
            _toastMessage.value = "Se encontraron errores en el formulario, inténtelo de nuevo"
        }
    }

    fun clearToastMessage() {
        _toastMessage.value = null
    }
}