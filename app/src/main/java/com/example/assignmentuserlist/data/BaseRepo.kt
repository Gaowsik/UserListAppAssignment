package com.example.assignmentuserlist.data

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

abstract class BaseRepo() {
    suspend fun <T : Any> safeApiCall(
        apiCall: suspend () -> T,
    ): APIResource<T> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiCall.invoke()
                APIResource.Success(response)
            } catch (throwable: Throwable) {
                when (throwable) {
                    is HttpException -> {
                        APIResource.Error(
                            false,
                            throwable.code(),
                            throwable.response()?.errorBody()
                        )
                    }

                    else -> {
                        APIResource.Error(true, null, null)
                    }
                }
            }
        }

    }
}