package com.example.homework20.domain.local.usecase.database

import com.example.homework20.domain.local.model.GetUsers
import com.example.homework20.domain.local.repository.UsersRepository
import javax.inject.Inject

class UpdateUserUseCase @Inject constructor(
    private val usersRepository: UsersRepository
) {
    suspend operator fun invoke(getUsers: GetUsers) {
        usersRepository.updateUser(getUsers)
    }
}