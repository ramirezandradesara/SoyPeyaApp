package com.soyhenry.library.utils.validator

object EmailValidator {
    private val EMAIL_REGEX = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[a-zA-Z]{2,6}$")

    fun validate(email: String): ValidationResult {
        return when {
            email.isBlank() -> ValidationResult(false, "El email es obligatorio")
            !EMAIL_REGEX.matches(email) -> ValidationResult(false, "Formato de email inválido")
            else -> ValidationResult(true)
        }
    }
}
