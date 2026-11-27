package com.uts.tamuin166.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.uts.tamuin166.data.entity.GuestEntity166
import kotlinx.coroutines.flow.Flow

@Dao
interface GuestDao166 {

    @Insert
    suspend fun insertGuest166(guest166: GuestEntity166)

    @Update
    suspend fun updateGuest166(guest166: GuestEntity166)

    @Delete
    suspend fun deleteGuest166(guest166: GuestEntity166)

    @Query("SELECT * FROM tabel_guest_166 WHERE id_166 = :guestId166 AND user_id_166 = :userId166 LIMIT 1")
    suspend fun getGuestById166(guestId166: Int, userId166: Int): GuestEntity166?

    @Query("SELECT * FROM tabel_guest_166 WHERE user_id_166 = :userId166 ORDER BY timestamp_166 DESC")
    fun getGuestsByUser166(userId166: Int): Flow<List<GuestEntity166>>

    @Query("SELECT * FROM tabel_guest_166 WHERE user_id_166 = :userId166 ORDER BY timestamp_166 DESC")
    fun getAllGuestsByUser166(userId166: Int): Flow<List<GuestEntity166>>

    @Query("SELECT COUNT(*) FROM tabel_guest_166 WHERE user_id_166 = :userId166 AND timestamp_166 BETWEEN :startOfDay166 AND :endOfDay166")
    fun getDailyGuestCount166(userId166: Int, startOfDay166: Long, endOfDay166: Long): Flow<Int>

    @Query("SELECT COUNT(*) FROM tabel_guest_166 WHERE user_id_166 = :userId166")
    fun getTotalGuestCount166(userId166: Int): Flow<Int>

    // shortcut: SQLite LIKE substring matching for offline search, upgrade to Room FTS4/FTS5 when dataset exceeds 10,000 records.
    @Query("""
        SELECT * FROM tabel_guest_166 
        WHERE user_id_166 = :userId166 
        AND (nama_166 LIKE '%' || :keyword166 || '%' 
             OR keperluan_166 LIKE '%' || :keyword166 || '%' 
             OR alamat_166 LIKE '%' || :keyword166 || '%') 
        ORDER BY timestamp_166 DESC
    """)
    fun searchGuests166(userId166: Int, keyword166: String): Flow<List<GuestEntity166>>

    @Query("""
        SELECT * FROM tabel_guest_166 
        WHERE user_id_166 = :userId166 
        AND (nama_166 LIKE '%' || :keyword166 || '%' 
             OR keperluan_166 LIKE '%' || :keyword166 || '%' 
             OR alamat_166 LIKE '%' || :keyword166 || '%') 
        ORDER BY timestamp_166 DESC
    """)
    fun searchGuestsByUser166(userId166: Int, keyword166: String): Flow<List<GuestEntity166>>
}
