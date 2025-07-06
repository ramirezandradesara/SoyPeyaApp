package com.soyhenry.feature.register.viewmodel

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.soyhenry.feature.register.domain.usecase.RegisterUseCase
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

    fun onRegisterClick() {
        val isEmailValid = validateEmail()
        val isNameValid = validateName()
        val isPasswordValid = validatePassword()
        val isConfirmPasswordValid = validateConfirmPassword()

        if (isEmailValid && isNameValid && isPasswordValid && isConfirmPasswordValid) {
            viewModelScope.launch {
                try {
                    registerUseCase(
                        email = _email.value,
                        name = _name.value,
                        password = _password.value
                    )
                    _toastMessage.value = "Registration successful"
                    _registerSuccess.value = true
                } catch (e: HttpException) {
                    val message = when (e.code()) {
                        400 -> "Bad request: please check your input"
                        403 -> "Forbidden: you are not allowed"
                        409 -> "Email already registered"
                        500 -> "Server error. Please try again later"
                        else -> "Unknown error: ${e.code()}"
                    }
                    _toastMessage.value = "Registration failed: $message"
                } catch (e: IOException) {
                    _toastMessage.value = "Network error: please check your connection"
                } catch (e: Exception) {
                    _toastMessage.value = "Unexpected error: ${e.message}"
                }
            }
        } else {
            _toastMessage.value = "Please fix the errors in the form"
        }
    }


    fun clearToastMessage() {
        _toastMessage.value = null
    }
}