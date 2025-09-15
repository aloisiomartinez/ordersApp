package com.example.kingburguer.viewmodels

import android.util.Log
import android.util.Patterns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kingburguer.compose.signup.FieldState
import com.example.kingburguer.compose.signup.FormState
import com.example.kingburguer.compose.signup.SignUpUiState
import com.example.kingburguer.validation.BirthdayValidator
import com.example.kingburguer.validation.ConfirmPasswordValidator
import com.example.kingburguer.validation.DocumentValidator
import com.example.kingburguer.validation.EmailValidator
import com.example.kingburguer.validation.Mask
import com.example.kingburguer.validation.NameValidator
import com.example.kingburguer.validation.PasswordValidator
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class SignUpViewModel: ViewModel() {

    private val _uiState = MutableStateFlow(SignUpUiState())
    val uiState: StateFlow<SignUpUiState> = _uiState.asStateFlow()

    var formState by mutableStateOf(FormState())

    private val emailValidator = EmailValidator()
    private val nameValidator = NameValidator()
    private val passwordValidator = PasswordValidator()
    private val confirmPasswordValidator = ConfirmPasswordValidator()
    private val documentValidator = DocumentValidator()
    private val birthdayValidator = BirthdayValidator()

    fun updateEmail(newEmail: String) {
        val textString = emailValidator.validate(newEmail)

        formState = formState.copy(
            email = FieldState(field = newEmail, error = textString)
        )
    }

    fun updateName(newName: String) {
       val textString = nameValidator.validate(newName)

        formState = formState.copy(
            name = FieldState(field = newName, error = textString)
        )
    }

    fun updatePassword(newPassword: String) {
        val textString = passwordValidator.validate(newPassword)

        formState = formState.copy(
            password = FieldState(field = newPassword, error = textString)
        )
    }

    fun updateConfirmPassword(newConfirmPassword: String) {
        val textString = confirmPasswordValidator.validate(newConfirmPassword, formState.password.field)

        formState = formState.copy(
            confirmPassword = FieldState(field = newConfirmPassword, error = textString)
        )
    }

    fun updateDocument(newDocument: String) {
        val textString = documentValidator.validate(formState.document.field, newDocument)
        formState = formState.copy(
            document = FieldState(field = documentValidator.result, error = textString)
        )
    }

    fun updateBirthday(newBirthday: String) {
        val textString = birthdayValidator.validate(formState.birthday.field, newBirthday)

        formState = formState.copy(
            birthday = FieldState(field = birthdayValidator.result, error = textString)
        )

    }

    fun send() {
         _uiState.update {
            it.copy(isLoading = true)

        }

        // Simulação de latencia de rede/ atraso de chamada
        viewModelScope.launch {
            delay(3000)
            _uiState.update {
                it.copy(isLoading = false, goToHome = true)
            }
        }
    }

    fun reset() {
        _uiState.update {
            SignUpUiState()
        }
    }


}
