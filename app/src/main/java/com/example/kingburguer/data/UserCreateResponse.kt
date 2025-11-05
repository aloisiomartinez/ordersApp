package com.example.kingburguer.data

sealed class UserCreateResponse {
    data class Sucess(
        val id: Int,
        val name: String,
        val email: String,
        val document: String,
        val birthday: String
    ): UserCreateResponse()

}

