package com.example.kingburguer.validation

import com.example.kingburguer.R
import com.example.kingburguer.compose.signup.FieldState

class PasswordValidator {

    fun validate(confirmPassword: String, password: String, ): TextString? {
        if (password.isBlank()) {
            return ResourceString(R.string.error_password_blank)
        }

        if (password.length < 8) {
            return ResourceString(R.string.error_password_invalid)
        }

        if (confirmPassword.isNotBlank() && confirmPassword != password) {
            return ResourceString(R.string.error_confirm_password_invalid)

        }

        return null
    }

    fun validate(password: String, ): TextString? {
        if (password.isBlank()) {
            return ResourceString(R.string.error_password_blank)
        }

        if (password.length < 8) {
            return ResourceString(R.string.error_password_invalid)
        }

        return null
    }
}