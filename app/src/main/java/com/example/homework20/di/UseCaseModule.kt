package com.example.homework20.di

import com.example.homework20.domain.local.repository.UsersRepository
import com.example.homework20.domain.local.usecase.CountUsersUseCase
import com.example.homework20.domain.local.usecase.InsertUserUseCase
import com.example.homework20.domain.local.usecase.RemoveUserUseCase
import com.example.homework20.domain.local.usecase.UpdateUserUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    @Singleton
    @Provides
    fun provideInsertUserUseCase(
        usersRepository: UsersRepository
    ): InsertUserUseCase {
        return InsertUserUseCase(usersRepository)
    }

    @Singleton
    @Provides
    fun provideRemoveUserUseCase(
        usersRepository: UsersRepository
    ): RemoveUserUseCase {
        return RemoveUserUseCase(usersRepository)
    }

    @Singleton
    @Provides
    fun provideUpdateUserUseCase(
        usersRepository: UsersRepository
    ): UpdateUserUseCase {
        return UpdateUserUseCase(usersRepository)
    }

    @Singleton
    @Provides
    fun provideCountUsersUseCase(
        usersRepository: UsersRepository
    ): CountUsersUseCase {
        return CountUsersUseCase(usersRepository)
    }
}