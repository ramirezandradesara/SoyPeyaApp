package com.soyhenry.library.utils.validator

object PasswordValidator {
    private const val PASSWORD_MIN_LENGTH = 8
    fun validate(password: String): ValidationResult {
        return when {
            password.isBlank() -> ValidationResult(false, "La contraseña es obligatoria")
            password.length < PASSWORD_MIN_LENGTH -> ValidationResult(false, "Debe tener al menos ${PASSWORD_MIN_LENGTH} caracteres")
            else -> ValidationResult(true)
        }
    }
}
