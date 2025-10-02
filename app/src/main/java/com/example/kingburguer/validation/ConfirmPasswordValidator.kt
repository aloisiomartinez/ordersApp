package com.example.kingburguer.validation

import com.example.kingburguer.R
import com.example.kingburguer.compose.signup.FieldState

class ConfirmPasswordValidator {

    fun validate(confirmPassword: String, password: String): TextString? {
        if (confirmPassword.isBlank()) {
            return ResourceString(R.string.error_confirm_password_blank)
        }

        if (password.isNotBlank() && confirmPassword != password) {
                return ResourceString(R.string.error_confirm_password_invalid)
        }
        return null
    }
}