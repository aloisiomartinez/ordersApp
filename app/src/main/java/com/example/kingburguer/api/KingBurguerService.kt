package com.example.kingburguer.api

import com.example.kingburguer.BuildConfig
import com.example.kingburguer.data.UserRequest
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface KingBurguerService {

    @POST("users")
    suspend fun postUser(
        @Body userRequest: UserRequest,
        @Header("x-secret-key") secretKey: String = BuildConfig.X_SECRET_KEY
    ): Response<ResponseBody>

    companion object {

        private const val BASE_URL = "https://hades.tiagoaguiar.co/kingburguer/"

        fun create(): KingBurguerService {
            val logger = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }

            val clientOk = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(clientOk)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(KingBurguerService::class.java)
        }
    }

}