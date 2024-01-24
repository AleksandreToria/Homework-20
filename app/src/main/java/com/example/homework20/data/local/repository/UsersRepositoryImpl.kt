package com.example.homework20.data.local.repository

import com.example.homework20.data.local.dao.UsersDao
import com.example.homework20.data.local.mapper.toData
import com.example.homework20.domain.local.model.GetUsers
import com.example.homework20.domain.local.repository.UsersRepository
import javax.inject.Inject

class UsersRepositoryImpl @Inject constructor(
    private val usersDao: UsersDao
) : UsersRepository {
    override suspend fun insertUser(getUsers: GetUsers) {
        return usersDao.insertUser(getUsers.toData())
    }

    override suspend fun removeUser(getUsers: GetUsers) {
        return usersDao.removeUser(getUsers.toData())
    }

    override suspend fun updateUser(getUsers: GetUsers) {
        return usersDao.updateUser(getUsers.toData())
    }

    override suspend fun countUsers(): Int {
        return usersDao.countUsers()
    }
}