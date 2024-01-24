package com.example.homework20.presentation.state

import com.example.homework20.presentation.model.Users

data class UsersState(
    val insertUser: Users? = null,
    val updateUser: Users? = null,
    val removeUser: Users? = null,
    val countUsers: Int = 0
)