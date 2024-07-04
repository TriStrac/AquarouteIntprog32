package com.cansal.aquaroute.network

import com.cansal.aquaroute.models.LoginRequest
import com.cansal.aquaroute.models.LoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
interface ApiService {

    @POST("/loginAccount")
    fun loginPost(@Body post: LoginRequest): Call<LoginResponse>

}