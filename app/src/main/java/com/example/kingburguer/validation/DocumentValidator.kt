package com.example.kingburguer.validation

import com.example.kingburguer.R
import com.example.kingburguer.compose.signup.FieldState

class DocumentValidator {

    private val pattern = "###.###.###-##"
    lateinit var result: String

    fun validate(currentDocument: String, document: String): TextString? {
        result = Mask(pattern, currentDocument, document)

        if (result.isBlank()) {
            return ResourceString(R.string.error_document_blank)
        }

        if (result.length != pattern.length) {
            return ResourceString(R.string.error_document_invalid)
        }

        return null
    }

}