package com.example.kingburguer.compose.signup

import java.lang.reflect.Field

data class FieldState (
    val field: String = "",
    val error: String? = null,
)

data class FormState(
    val email: FieldState = FieldState(),
    val name: FieldState = FieldState(),
    val password: FieldState = FieldState(),
    val confirmPassword: FieldState = FieldState()
)