package com.uts.tamuin159.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.uts.tamuin159.data.dao.GuestDao159
import com.uts.tamuin159.data.dao.UserDao159
import com.uts.tamuin159.data.entity.GuestEntity159
import com.uts.tamuin159.data.entity.UserEntity159

@Database(
    entities = [UserEntity159::class, GuestEntity159::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase159 : RoomDatabase() {

    abstract fun userDao159(): UserDao159
    abstract fun guestDao159(): GuestDao159

    companion object {
        @Volatile
        private var INSTANCE159: AppDatabase159? = null

        fun getDatabase159(context159: Context): AppDatabase159 {
            return INSTANCE159 ?: synchronized(this) {
                val instance159 = Room.databaseBuilder(
                    context159.applicationContext,
                    AppDatabase159::class.java,
                    "db_tamuin_159"
                ).build()
                INSTANCE159 = instance159
                instance159
            }
        }
    }
}
