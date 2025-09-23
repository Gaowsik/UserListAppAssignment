package com.example.assignmentuserlist.domain

import com.example.assignmentuserlist.data.APIResource

interface UserRepository {
    suspend fun getUsers(count: Int): APIResource<List<User>>
}