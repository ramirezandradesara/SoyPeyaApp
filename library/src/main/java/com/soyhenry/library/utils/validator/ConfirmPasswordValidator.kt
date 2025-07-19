package com.soyhenry.library.utils.validator
import android.content.Context

object ConfirmPasswordValidator {
    fun validate(password: String, confirmPassword: String): ValidationResult {
        return when {
            confirmPassword.isBlank() -> ValidationResult(false, "Confirme su contraseña")
            confirmPassword != password -> ValidationResult(false, "Las contraseñas no coinciden")
            else -> ValidationResult(true)
        }
    }
}
