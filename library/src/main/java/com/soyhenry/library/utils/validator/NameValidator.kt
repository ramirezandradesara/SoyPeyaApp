package com.soyhenry.library.utils.validator

object NameValidator {
    fun validate(email: String): ValidationResult {
        return when {
            email.isBlank() -> ValidationResult(false, "El nombre es obligatorio")
            else -> ValidationResult(true)
        }
    }
}
