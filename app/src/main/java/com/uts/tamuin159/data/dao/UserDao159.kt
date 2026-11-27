package com.uts.tamuin166.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.uts.tamuin166.data.entity.UserEntity166

@Dao
interface UserDao166 {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun registerUser166(user166: UserEntity166): Long

    @Query("SELECT * FROM tabel_user_166 WHERE username_166 = :username166 LIMIT 1")
    suspend fun getUserByUsername166(username166: String): UserEntity166?

    @Query("SELECT * FROM tabel_user_166 WHERE username_166 = :username166 LIMIT 1")
    suspend fun checkUsernameExists166(username166: String): UserEntity166?

    @Query("SELECT * FROM tabel_user_166 WHERE username_166 = :username166 AND password_166 = :password166 LIMIT 1")
    suspend fun loginUser166(username166: String, password166: String): UserEntity166?
}
