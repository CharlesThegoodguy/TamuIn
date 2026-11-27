package com.uts.tamuin166.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.uts.tamuin166.data.dao.GuestDao166
import com.uts.tamuin166.data.dao.UserDao166
import com.uts.tamuin166.data.entity.GuestEntity166
import com.uts.tamuin166.data.entity.UserEntity166

@Database(
    entities = [UserEntity166::class, GuestEntity166::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase166 : RoomDatabase() {

    abstract fun userDao166(): UserDao166
    abstract fun guestDao166(): GuestDao166

    companion object {
        @Volatile
        private var INSTANCE166: AppDatabase166? = null

        fun getDatabase166(context166: Context): AppDatabase166 {
            return INSTANCE166 ?: synchronized(this) {
                val instance166 = Room.databaseBuilder(
                    context166.applicationContext,
                    AppDatabase166::class.java,
                    "db_tamuin_166"
                ).build()
                INSTANCE166 = instance166
                instance166
            }
        }
    }
}
