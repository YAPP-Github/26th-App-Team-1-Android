package com.ninecraft.booket.core.network.service

import com.ninecraft.booket.core.network.response.UserProfileResponse
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthService {
    @POST("api/v1/auth/signout")
    suspend fun logout()

    @GET("api/v1/auth/me")
    suspend fun getUserProfile(): UserProfileResponse
}
