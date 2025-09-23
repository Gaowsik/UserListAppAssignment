package com.example.assignmentuserlist.data.model

import com.example.assignmentuserlist.domain.User

fun ApiUser.toUser(): User {
    return User(
        userId = login.uuid,
        fullName = name.getFullName(),
        email = email,
        phone = phone,
        imageLargeUrl = picture.large,
        imageThumbnailUrl = picture.thumbnail
    )
}