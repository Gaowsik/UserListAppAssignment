package com.example.assignmentuserlist.data

import okhttp3.ResponseBody

sealed class APIResource<out T> {
    data class Success<out T>(val value: T) : APIResource<T>()
    data class Error(
        val isNetworkError: Boolean,
        val errorCode: Int?,
        val errorBody: ResponseBody?
    ) : APIResource<Nothing>()
    object Loading : APIResource<Nothing>()
}