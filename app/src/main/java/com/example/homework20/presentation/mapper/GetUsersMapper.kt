package com.example.homework20.presentation.mapper

import com.example.homework20.domain.local.model.GetUsers
import com.example.homework20.presentation.model.Users

fun Users.toDomain(): GetUsers {
    return GetUsers(
        email = email,
        firstName = firstName,
        lastName = lastName,
        age = age
    )
}