package com.example.kingburguer.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.kingburguer.api.KingBurguerService
import com.example.kingburguer.compose.login.LoginFormState
import com.example.kingburguer.compose.login.LoginUiState
import com.example.kingburguer.compose.signup.FieldState
import com.example.kingburguer.data.ApiResult
import com.example.kingburguer.data.KingBurguerLocalStorage
import com.example.kingburguer.data.KingBurguerRepository
import com.example.kingburguer.data.LoginRequest
import com.example.kingburguer.data.LoginResponse
import com.example.kingburguer.data.UserCreateResponse
import com.example.kingburguer.data.UserRequest
import com.example.kingburguer.validation.EmailValidator
import com.example.kingburguer.validation.PasswordValidator
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

class LoginViewModel(
    private val repository: KingBurguerRepository
): ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    var formState by mutableStateOf(LoginFormState())

    private val emailValidator = EmailValidator()
    private val passwordValidator = PasswordValidator()

    fun updateEmail(newEmail: String) {
        val textString = emailValidator.validate(newEmail)
        formState = formState.copy(
            email = FieldState(field = newEmail, error = textString, isValid = textString == null)
        )

        updateButton()
    }

    fun updatePassword(newPassword: String) {
        var textString = passwordValidator.validate(newPassword)
        formState = formState.copy(
            password = FieldState(
                field = newPassword,
                error = textString,
                isValid = textString == null
            )
        )

        updateButton()
    }

    private fun updateButton() {
        val formIsValid = with(formState) {
            email.isValid && password.isValid

        }

        formState = formState.copy(
            formIsValid = formIsValid
        )
    }

    fun updateRememberMe(rememberMe: Boolean) {
        formState = formState.copy(
            rememberMe = rememberMe
        )
    }

    fun send() {
         _uiState.update {
            it.copy(isLoading = true)
        }

        // Simulação de latencia de rede/ atraso de chamada
        viewModelScope.launch {
            with(formState) {

                val loginRequest = LoginRequest(
                    username = email.field,
                    password = password.field
                )

                val result = repository.login(loginRequest, rememberMe)

                when(result) {
                    is ApiResult.Success -> {
                        _uiState.update { it.copy(isLoading = false, goToHome = true) }

                    }
                    is ApiResult.Error -> {
                        _uiState.update { it.copy(isLoading = false, error = result.message) }
                    }
                }
            }
        }
    }

    fun reset() {
        _uiState.update {
            LoginUiState()
        }
    }

    companion object {
        val factory = viewModelFactory {
            initializer {
                val application = this[APPLICATION_KEY]!!.applicationContext
                val service = KingBurguerService.create()
                val localStorage = KingBurguerLocalStorage(application)
                val repository = KingBurguerRepository(service,localStorage )
                LoginViewModel(repository)
            }
        }
    }

}
