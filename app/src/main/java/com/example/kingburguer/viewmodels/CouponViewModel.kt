package com.example.kingburguer.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.kingburguer.api.KingBurguerService
import com.example.kingburguer.compose.coupon.CouponUiState
import com.example.kingburguer.compose.home.HomeUiState
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
import java.util.Date
import java.util.Locale

data class Coupon(
    val id: String,
    val name: String,
    val expirationDate: String // "dd/MM/yyyy HH:mm:ss"
)

enum class CouponFilter {
    ACTIVE, EXPIRED, ALL
}

class CouponViewModel(
    private val repository: KingBurguerRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CouponUiState())
    val uiState: StateFlow<CouponUiState> = _uiState.asStateFlow()

    private val _selectedFilter = MutableStateFlow(CouponFilter.ALL)
    val selectedFilter: StateFlow<CouponFilter> = _selectedFilter.asStateFlow()

    private val _filteredCoupons = MutableStateFlow<List<Coupon>>(emptyList())
    val filteredCoupons: StateFlow<List<Coupon>> = _filteredCoupons.asStateFlow()


    init {
        loadCoupons()
    }

    fun loadCoupons() {
        _uiState.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            val response = repository.fetchCoupons()

            when (response) {
                is ApiResult.Error -> {
                    _uiState.update { it.copy(isLoading = false, error = response.message) }
                }

                is ApiResult.Success -> {
                    _uiState.update { it.copy(isLoading = false, coupons = response.data) }
                }
            }

        }
    }


    companion object {
        val factory = viewModelFactory {
            initializer {
                val application = this[APPLICATION_KEY]!!.applicationContext
                val localStorage = KingBurguerLocalStorage(application)
                val service = KingBurguerService.create()
                val repository = KingBurguerRepository(service, localStorage)
                CouponViewModel(repository)
            }
        }
    }

}
