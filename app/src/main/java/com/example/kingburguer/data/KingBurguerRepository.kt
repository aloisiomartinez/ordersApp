package com.example.kingburguer.data

import com.example.kingburguer.api.KingBurguerService
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
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

    suspend fun fetchFeed(): ApiResult<FeedResponse> {
        val userCredentials = localStorage.fetchInitialUserCredentials()
        val token = "${userCredentials.tokenType} ${userCredentials.accessToken}"

        var result =  apiCall {
            service.fetchFeed(token)
        }

        return result
    }

    suspend fun fetchProductById(productId: Int): ApiResult<ProductDetailResponse> {
        val userCredentials = localStorage.fetchInitialUserCredentials()
        val token = "${userCredentials.tokenType} ${userCredentials.accessToken}"

        return apiCall {  service.fetchProductById(token, productId) }
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

    suspend fun createCoupon(productId: Int): ApiResult<CouponResponse> {
        val userCredentials = localStorage.fetchInitialUserCredentials()
        val token = "${userCredentials.tokenType} ${userCredentials.accessToken}"

        return apiCall {  service.createCoupon(token, productId) }
    }

    suspend fun fetchMe(): ApiResult<ProfileResponse> {
        val userCredentials = localStorage.fetchInitialUserCredentials()
        val token = "${userCredentials.tokenType} ${userCredentials.accessToken}"

        return apiCall {  service.fetchMe(token) }
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
                        try {
                            val errorAuth = Gson().fromJson(json, ErrorAuth::class.java)
                            ApiResult.Error(errorAuth.detail.message)
                        } catch (e: JsonSyntaxException) {
                            val error = Gson().fromJson(json, Error::class.java)
                            ApiResult.Error(error.detail)
                        }
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