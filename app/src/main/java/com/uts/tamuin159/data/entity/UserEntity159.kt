package com.uts.tamuin159.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "tabel_user_159",
    indices = [
        Index(value = ["username_159"], unique = true)
    ]
)
data class UserEntity159(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_159")
    val id_159: Int = 0,

    @ColumnInfo(name = "username_159")
    val username_159: String,

    // shortcut: plaintext password storage for offline UTS assignment, upgrade to bcrypt/Argon2 hashing for production apps.
    @ColumnInfo(name = "password_159")
    val password_159: String
)
