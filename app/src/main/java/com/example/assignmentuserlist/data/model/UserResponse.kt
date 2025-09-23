package com.example.assignmentuserlist.data.model

import kotlinx.serialization.SerialName

data class UserResponse(
    @SerialName("results") val results: List<ApiUser>
)