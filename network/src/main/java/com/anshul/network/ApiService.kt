package com.anshul.network

import com.anshul.entities.dto.LoginResponse
import com.anshul.entities.dto.NetworkChatMessage
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*


/**
 * @author anshulgoel
 * at 02/09/20, 8:09 PM
 * for ChatBook
 */

// This can be provided via gradle properties or build config if support for multiple environments is required.
private const val BASE_URL = "/*removed*/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .build()

interface ApiService {

    @Headers("Content-Type:application/json")
    @POST("api/login")
    fun loginAsync(
        @Query("username") userName: String,
        @Query("password") password: String
    ): Deferred<LoginResponse>

    @Headers("Content-Type:application/json")
    @GET("api/messages")
    fun getMessagesAsync(@Header("X-Auth-Token") authToken: String): Deferred<List<NetworkChatMessage>>

    @Headers("Content-Type:application/json")
    @POST("api/messages")
    fun sendMessageAsync(
        @Header("X-Auth-Token") authToken: String,
        @Query("thread_id") threadID: Int,
        @Query("body") body: String
    ): Deferred<NetworkChatMessage>
}

object Api {
    val apiService: ApiService by lazy { retrofit.create(ApiService::class.java) }
}