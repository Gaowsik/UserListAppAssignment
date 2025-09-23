package com.example.assignmentuserlist.domain

data class User(
    val fullName: String,
    val email: String,
    val phone: String,
    val imageLargeUrl: String,
    val imageThumbnailUrl: String
)
