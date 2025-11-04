package com.example.kingburguer.data

import com.example.kingburguer.api.KingBurguerService
import com.google.gson.Gson
import retrofit2.Response

class KingBurguerRepository(
    private val service: KingBurguerService,
    private val localStorage: KingBurguerLocalStorage
) {

    // "get"
    suspend fun fetchInitialCredentials() = localStorage.fetchInitialUserCredentials()

    suspend fun postUser(userRequest: UserRequest): ApiResult<UserCreateResponse> {
        val result = apiCall {
            service.postUser(userRequest)
        }
        return result
    }

    suspend fun login(loginRequest: LoginRequest, keepLogged: Boolean): ApiResult<LoginResponse> {
        val result = apiCall {
            service.login(loginRequest)
        }

        if (result is ApiResult.Success<LoginResponse>) {
            if (keepLogged) {
               updateCredentials(result.data)
            }
        }

        return result
    }

    suspend fun refreshToken(request: RefreshTokenRequest): ApiResult<LoginResponse> {
        val userCredentials = localStorage.fetchInitialUserCredentials()

        val result = apiCall {
            service.refreshToken(
                request,
                "${userCredentials.tokenType} ${userCredentials.accessToken}"
            )
        }

        if (result is ApiResult.Success<LoginResponse>) {
            updateCredentials(result.data)

        }

        return result
    }

    private suspend fun updateCredentials(data: LoginResponse) {
        val userCredentials = UserCredentials(
            accessToken = data.accessToken,
            refreshToken = data.refreshToken,
            expiresTimestamp = data.expiresSeconds.toLong(),
            tokenType = data.tokenType,
        )
        localStorage.updateUserCredential(userCredentials)
    }

//    suspend fun fetchCoupons(loginRequest: LoginRequest): LoginResponse {
//        try {
//            val response = service.login(loginRequest)
//
//            if (!response.isSuccessful) {
//                val errorData = response.errorBody()?.string()?.let { json ->
//                    Gson().fromJson(json, LoginResponse.ErrorAuth::class.java)
//                }
//
//                return errorData ?: LoginResponse.Error("Internal Server Error")
//            }
//
//            val data = response.body()?.string()?.let { json ->
//                Gson().fromJson(json, LoginResponse.Success::class.java)
//            }
//
//            return data?: LoginResponse.Error("Unexpected response")
//        } catch (e: Exception) {
//            return LoginResponse.Error(e.message ?: "unexpected exception")
//        }
//    }

    private suspend fun <T> apiCall(call: suspend () -> Response<T>): ApiResult<T> {
        try {
            val response = call()

            if (!response.isSuccessful) {
                val errorData = response.errorBody()?.string()?.let { json ->
                    if (response.code() == 401) {
                        val errorAuth = Gson().fromJson(json, ErrorAuth::class.java)
                        ApiResult.Error(errorAuth.detail.message)
                    } else {
                        Gson().fromJson(json, ApiResult.Error::class.java)
                    }
                }

                return errorData ?: ApiResult.Error("Internal Server Error")
            }

            // success
            val data = response.body()

            if (data == null) {
                return ApiResult.Error("Unexpected response")
            }

            return ApiResult.Success(data)
        } catch (e: Exception) {
            return ApiResult.Error(e.message ?: "unexpected exception")
        }
    }

}