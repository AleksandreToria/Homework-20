package com.example.homework20.di

import com.example.homework20.data.local.dao.UsersDao
import com.example.homework20.data.local.repository.UsersRepositoryImpl
import com.example.homework20.domain.local.repository.UsersRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideUsersRepository(usersDao: UsersDao): UsersRepository {
        return UsersRepositoryImpl(usersDao)
    }
}