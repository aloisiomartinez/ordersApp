package com.example.kingburguer.compose.login

import com.example.kingburguer.compose.signup.FieldState

data class LoginFormState(
    val email: FieldState = FieldState(),
    val password: FieldState = FieldState(),
    val rememberMe: Boolean = false,
    val formIsValid: Boolean = false
)