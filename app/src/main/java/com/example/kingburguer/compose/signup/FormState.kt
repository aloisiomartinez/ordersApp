package com.example.kingburguer.compose.signup

import java.lang.reflect.Field

data class FieldState (
    val field: String = "",
    val error: String? = null,
)

data class FormState(
    val name: FieldState = FieldState(),
    val nameError: String? = null,
)