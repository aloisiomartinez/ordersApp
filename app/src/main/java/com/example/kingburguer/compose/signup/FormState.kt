package com.example.kingburguer.compose.signup

import com.example.kingburguer.validation.TextString


data class FieldState (
    val field: String = "",
    val error: TextString? = null,
    val isValid: Boolean = false
)

data class FormState(
    val email: FieldState = FieldState(),
    val name: FieldState = FieldState(),
    val password: FieldState = FieldState(),
    val confirmPassword: FieldState = FieldState(),
    val document: FieldState = FieldState(),
    val birthday: FieldState = FieldState(),
    val formIsValid: Boolean = false
)