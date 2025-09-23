package com.example.assignmentuserlist.domain

import com.example.assignmentuserlist.data.APIResource
import com.example.assignmentuserlist.data.model.UserResponse

interface UserRepository {
    suspend fun getUsers(): APIResource<List<User>>
}