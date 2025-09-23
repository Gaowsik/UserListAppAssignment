package com.example.assignmentuserlist.data.source

import com.example.assignmentuserlist.data.APIResource
import com.example.assignmentuserlist.data.model.UserResponse
import com.example.assignmentuserlist.domain.User

interface UserDataSource {
    suspend fun getUsers(): APIResource<List<User>>
}