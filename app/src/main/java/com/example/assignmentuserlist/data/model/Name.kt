package com.example.assignmentuserlist.data.model

import kotlinx.serialization.SerialName

data class Name(
    @SerialName("title") val title: String,
    @SerialName("first") val first: String,
    @SerialName("last") val last: String
) {
    fun getFullName() = "$title $first $last"
}