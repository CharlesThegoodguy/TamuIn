package com.uts.tamuin159.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "tabel_guest_159",
    foreignKeys = [
        ForeignKey(
            entity = UserEntity159::class,
            parentColumns = ["id_159"],
            childColumns = ["user_id_159"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class GuestEntity159(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_159")
    val id_159: Int = 0,

    @ColumnInfo(name = "user_id_159", index = true)
    val user_id_159: Int,

    @ColumnInfo(name = "nama_159")
    val nama_159: String,

    @ColumnInfo(name = "alamat_159")
    val alamat_159: String,

    @ColumnInfo(name = "keperluan_159")
    val keperluan_159: String,

    @ColumnInfo(name = "timestamp_159")
    val timestamp_159: Long
)
