package com.example.homework20.domain.local.usecase.database

import com.example.homework20.domain.local.repository.UsersRepository
import javax.inject.Inject

class CheckUserUseCase @Inject constructor(
    private val usersRepository: UsersRepository
) {
    suspend operator fun invoke(email: String): Int {
        return usersRepository.checkUser(email)
    }
}