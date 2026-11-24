package com.uts.tamuin159.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.uts.tamuin159.data.entity.UserEntity159

@Dao
interface UserDao159 {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun registerUser159(user159: UserEntity159): Long

    @Query("SELECT * FROM tabel_user_159 WHERE username_159 = :username159 LIMIT 1")
    suspend fun getUserByUsername159(username159: String): UserEntity159?

    @Query("SELECT * FROM tabel_user_159 WHERE username_159 = :username159 LIMIT 1")
    suspend fun checkUsernameExists159(username159: String): UserEntity159?

    @Query("SELECT * FROM tabel_user_159 WHERE username_159 = :username159 AND password_159 = :password159 LIMIT 1")
    suspend fun loginUser159(username159: String, password159: String): UserEntity159?
}
