package com.example.assignmentuserlist.data.source

import com.example.assignmentuserlist.data.BaseRepo
import com.example.assignmentuserlist.data.model.toUser
import javax.inject.Inject

class UserDataSourceImpl @Inject constructor(
    private val userApi: UserApi
) : UserDataSource, BaseRepo() {
    override suspend fun getUsers(count : Int) = safeApiCall {
        userApi.getUsers(count).results.map {
            it.toUser()

        }
    }
}