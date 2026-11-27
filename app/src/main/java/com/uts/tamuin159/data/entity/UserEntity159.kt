package com.uts.tamuin166.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "tabel_user_166",
    indices = [
        Index(value = ["username_166"], unique = true)
    ]
)
data class UserEntity166(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_166")
    val id_166: Int = 0,

    @ColumnInfo(name = "username_166")
    val username_166: String,

    // shortcut: plaintext password storage for offline UTS assignment, upgrade to bcrypt/Argon2 hashing for production apps.
    @ColumnInfo(name = "password_166")
    val password_166: String
)
