package com.example.homework20.presentation.event

import com.example.homework20.presentation.model.Users

sealed class UsersEvents {
    data class InsertUser(val users: Users) : UsersEvents()
    data class RemoveUser(val users: Users) : UsersEvents()
    data class UpdateUser(val users: Users) : UsersEvents()
    data object CountUsers : UsersEvents()
}