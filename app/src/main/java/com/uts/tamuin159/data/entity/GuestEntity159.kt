package com.uts.tamuin166.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "tabel_guest_166",
    foreignKeys = [
        ForeignKey(
            entity = UserEntity166::class,
            parentColumns = ["id_166"],
            childColumns = ["user_id_166"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class GuestEntity166(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_166")
    val id_166: Int = 0,

    @ColumnInfo(name = "user_id_166", index = true)
    val user_id_166: Int,

    @ColumnInfo(name = "nama_166")
    val nama_166: String,

    @ColumnInfo(name = "alamat_166")
    val alamat_166: String,

    @ColumnInfo(name = "keperluan_166")
    val keperluan_166: String,

    @ColumnInfo(name = "timestamp_166")
    val timestamp_166: Long
)
