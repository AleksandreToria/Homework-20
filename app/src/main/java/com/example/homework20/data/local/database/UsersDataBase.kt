package com.example.homework20.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.homework20.data.local.dao.UsersDao
import com.example.homework20.data.local.model.UsersEntity

@Database(entities = [UsersEntity::class], version = 1)
abstract class UsersDataBase : RoomDatabase() {
    abstract fun usersDao(): UsersDao
}