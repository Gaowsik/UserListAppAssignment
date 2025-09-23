package com.example.assignmentuserlist.data.source

import com.example.assignmentuserlist.data.model.UserResponse
import retrofit2.http.GET

interface UserApi {
    @GET("api/")
    suspend fun getUsers(): UserResponse
}