package com.example.homework20.domain.local.usecase

import com.example.homework20.domain.local.repository.UsersRepository
import javax.inject.Inject

class CountUsersUseCase @Inject constructor(
    private val usersRepository: UsersRepository
) {
    suspend operator fun invoke(): Int {
        return usersRepository.countUsers()
    }
}