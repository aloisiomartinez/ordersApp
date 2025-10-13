package com.example.kingburguer.compose

enum class Screen(val route: String) {
    LOGIN("login"),
    SIGNUP("signup"),
    MAIN("main"),

    // Rotas da tela Principal
    HOME("home"),
    COUPON("coupon"),
    PROFILE("profile")
}