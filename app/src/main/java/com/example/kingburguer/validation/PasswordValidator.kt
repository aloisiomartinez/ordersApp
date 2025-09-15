package com.example.kingburguer.validation

import com.example.kingburguer.R
import com.example.kingburguer.compose.signup.FieldState

class PasswordValidator {

    fun validate(password: String): TextString? {
        if (password.isBlank()) {
            return ResourceString(R.string.error_password_blank)
        }

        if (password.length < 8) {
            return ResourceString(R.string.error_password_invalid)
        }

        return null
    }
}