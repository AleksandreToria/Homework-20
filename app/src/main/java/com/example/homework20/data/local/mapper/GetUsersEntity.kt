package com.example.homework20.data.local.mapper

import com.example.homework20.data.local.model.UsersEntity
import com.example.homework20.domain.local.model.GetUsers

fun GetUsers.toData(): UsersEntity {
    return UsersEntity(
        email = email,
        firstName = firstName,
        lastName = lastName,
        age = age
    )
}