package com.example.kingburguer.validation

import com.example.kingburguer.R

class NameValidator {

    fun validate(name: String): TextString? {
        if (name.isBlank()) {
            return ResourceString(R.string.error_name_blank)

        }

        if (name.length < 3) {
            return ResourceString(R.string.error_name_invalid)
        }

        return null
    }
}