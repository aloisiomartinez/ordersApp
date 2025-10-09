package com.example.kingburguer.data

import com.example.kingburguer.api.KingBurguerService
import com.google.gson.Gson

class KingBurguerRepository(
    private val service: KingBurguerService
) {

    suspend fun postUser(userRequest: UserRequest): UserCreateResponse {
        val response = service.postUser(userRequest)

        try {
            if (!response.isSuccessful) {
                val errorData = response.errorBody()?.string()?.let { json ->
                    if (response.code() == 401) {
                        Gson().fromJson(json, UserCreateResponse.ErrorAuth::class.java)
                    } else {
                        Gson().fromJson(json, UserCreateResponse.Error::class.java)
                    }
                }
                return errorData ?: UserCreateResponse.Error("Internal Server Error")
            }

            // Success
            val data = response.body()?.string()?.let { json ->
                Gson().fromJson(json, UserCreateResponse.Sucess::class.java)
            }

            return data ?: UserCreateResponse.Error("Unexpected response")
        } catch (e: Exception) {
            return UserCreateResponse.Error("Exception: ${e.message ?: "unexpected exception"}")
        }
    }

    suspend fun login(loginRequest: LoginRequest): LoginResponse {
        try {
            val response = service.login(loginRequest)

            if (!response.isSuccessful) {
                val errorData = response.errorBody()?.string()?.let { json ->
                    Gson().fromJson(json, LoginResponse.ErrorAuth::class.java)
                }

                return errorData ?: LoginResponse.Error("Internal Server Error")
            }

            val data = response.body()?.string()?.let { json ->
                Gson().fromJson(json, LoginResponse.Sucess::class.java)
            }

            return data?: LoginResponse.Error("Unexpected response")
        } catch (e: Exception) {
            return LoginResponse.Error(e.message ?: "unexpected exception")
        }
    }
}