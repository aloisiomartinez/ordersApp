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
import com.example.kingburguer.compose.login.LoginFormState
import com.example.kingburguer.compose.login.LoginUiState
import com.example.kingburguer.compose.signup.FieldState
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
): ViewModel() {

    private val _coupons = MutableStateFlow<List<Coupon>>(emptyList())
    val coupons: StateFlow<List<Coupon>> = _coupons.asStateFlow()

    private val _selectedFilter  = MutableStateFlow(CouponFilter.ALL)
    val selectedFilter: StateFlow<CouponFilter> = _selectedFilter.asStateFlow()

    private val _filteredCoupons = MutableStateFlow<List<Coupon>>(emptyList())
    val filteredCoupons: StateFlow<List<Coupon>> = _filteredCoupons.asStateFlow()

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    init {
        loadCoupons()
    }

    fun loadCoupons() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(isLoading = true)
            }

            val couponsMock = listOf(
                Coupon("1", "Cupom 1", "12/12/2025 16:23:32"),
                Coupon("2", "Cupom 2", "11/11/2024 14:20:10"),
                Coupon("3", "Cupom 3", "10/10/2023 12:15:05"),
                Coupon("4", "Cupom 4", "09/09/2022 10:10:00")
            )

            _coupons.value = couponsMock

            _uiState.update {
                it.copy(isLoading = false)
            }

            applyFilter()
        }
    }

    fun setFilter(filter: CouponFilter) {
        _selectedFilter.value = filter
        applyFilter()
    }

    private fun applyFilter() {
        _filteredCoupons.value = when (_selectedFilter.value) {
            CouponFilter.ACTIVE -> _coupons.value.filter { isCouponActive(it.expirationDate) }
            CouponFilter.EXPIRED -> _coupons.value.filter { !isCouponActive(it.expirationDate) }
            CouponFilter.ALL -> _coupons.value
        }
    }

    fun isCouponActive(dateString: String): Boolean {
        return try {
            val format = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
            val couponDate = format.parse(dateString)
            val currentDate = Date()
            couponDate?.after(currentDate) ?: false
        } catch (e: Exception) {
            false
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
