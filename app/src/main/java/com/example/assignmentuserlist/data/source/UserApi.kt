package com.example.assignmentuserlist.data.source

import com.example.assignmentuserlist.data.model.UserResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface UserApi {
    @GET("api/")
    suspend fun getUsers(@Query("results") count: Int): UserResponse
}