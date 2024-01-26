package com.example.homework20.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.homework20.data.local.model.UsersEntity

@Dao
interface UsersDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUser(user: UsersEntity)

    @Delete
    suspend fun removeUser(usersEntity: UsersEntity)

    @Update
    suspend fun updateUser(usersEntity: UsersEntity)

    @Query("SELECT COUNT(*) FROM users")
    suspend fun countUsers(): Int

    @Query("SELECT COUNT(*) FROM users WHERE email = :email")
    fun checkUser(email: String): Int
}