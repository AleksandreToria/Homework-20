package com.example.homework20.domain.local.usecase

import com.example.homework20.domain.local.model.GetUsers
import com.example.homework20.domain.local.repository.UsersRepository
import javax.inject.Inject

class RemoveUserUseCase @Inject constructor(
    private val usersRepository: UsersRepository
) {
    suspend operator fun invoke(getUsers: GetUsers) {
        usersRepository.removeUser(getUsers)
    }
}