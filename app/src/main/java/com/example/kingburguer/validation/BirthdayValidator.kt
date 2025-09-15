package com.example.kingburguer.validation

import com.example.kingburguer.R
import com.example.kingburguer.compose.signup.FieldState
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class BirthdayValidator {
    val pattern = "##/##/####"
    lateinit var result: String

    fun validate(currentBirthday: String, birthday: String): TextString? {
         result = Mask(pattern,currentBirthday,  birthday)

        if (result.isBlank()) {
            return ResourceString(R.string.error_birthday_blank)
        }

        // O numero precisa ser igual ao da mascara
        if (result.length != pattern.length) {
            return ResourceString(R.string.error_birthday_invalid)
        }

        // Validar data = 30/02/2020
        try {
            SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).run {
                isLenient = false
                parse(result)
            }?.also {
                val now = Date()
                if (it.after(now)) {
                    return ResourceString(R.string.error_birthday_future_invalid)
                }
            }


        } catch (e: ParseException) {
            return ResourceString(R.string.error_birthday_invalid)
        }

        return null
    }
}