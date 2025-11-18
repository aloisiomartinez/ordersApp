package com.example.kingburguer.compose.coupon

import com.example.kingburguer.data.CouponResponse

data class CouponUiState (
    val isLoading: Boolean = false,
    val coupons: List<CouponResponse>? = null,
    val error: String? = null
)