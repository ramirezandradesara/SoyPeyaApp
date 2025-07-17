package com.soyhenry.library.utils.validator

data class ValidationResult(
    val isValid: Boolean,
    val errorMessage: String? = null
)
