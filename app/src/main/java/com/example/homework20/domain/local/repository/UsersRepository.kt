package com.example.homework20.domain.local.repository

import com.example.homework20.domain.local.model.GetUsers

interface UsersRepository {
    suspend fun insertUser(getUsers: GetUsers)
    suspend fun removeUser(getUsers: GetUsers)
    suspend fun updateUser(getUsers: GetUsers)
    suspend fun countUsers(): Int
    suspend fun checkUser(email: String): Int
}