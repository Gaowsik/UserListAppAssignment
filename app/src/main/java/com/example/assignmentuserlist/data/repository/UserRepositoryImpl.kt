package com.example.assignmentuserlist.data.repository

import com.example.assignmentuserlist.data.source.UserDataSource
import com.example.assignmentuserlist.domain.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userDataSource: UserDataSource
) : UserRepository {
    override suspend fun getUsers() = userDataSource.getUsers()
}