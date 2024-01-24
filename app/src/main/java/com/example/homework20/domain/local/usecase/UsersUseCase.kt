package com.example.homework20.domain.local.usecase

import javax.inject.Inject

data class UsersUseCase @Inject constructor(
    val insertUserUseCase: InsertUserUseCase,
    val removeUserUseCase: RemoveUserUseCase,
    val updateUserUseCase: UpdateUserUseCase,
    val countUsersUseCase: CountUsersUseCase
)
