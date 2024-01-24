package com.example.homework20.di

import android.content.Context
import androidx.room.Room
import com.example.homework20.data.local.dao.UsersDao
import com.example.homework20.data.local.database.UsersDataBase
import com.example.homework20.data.local.repository.UsersRepositoryImpl
import com.example.homework20.domain.local.repository.UsersRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {
    @Provides
    @Singleton
    fun provideRoomDatabase(@ApplicationContext context: Context): UsersDataBase =
        Room.databaseBuilder(
            context, UsersDataBase::class.java, "USERS_DATABASE"
        )
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    fun provideDao(db: UsersDataBase) = db.usersDao()

    @Provides
    @Singleton
    fun provideUsersRepository(usersDao: UsersDao): UsersRepository {
        return UsersRepositoryImpl(usersDao)
    }
}