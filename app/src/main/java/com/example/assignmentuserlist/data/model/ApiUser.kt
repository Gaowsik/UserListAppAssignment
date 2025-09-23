package com.example.assignmentuserlist.data.model

import com.google.gson.annotations.SerializedName

data class ApiUser(
    @SerializedName("name") val name: Name,
    @SerializedName("email") val email: String,
    @SerializedName("phone") val phone: String,
    @SerializedName("picture") val picture: Picture,
    @SerializedName("login") val login: Login
)