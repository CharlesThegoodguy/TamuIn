package com.uts.tamuin159.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.uts.tamuin159.data.entity.GuestEntity159
import kotlinx.coroutines.flow.Flow

@Dao
interface GuestDao159 {

    @Insert
    suspend fun insertGuest159(guest159: GuestEntity159)

    @Update
    suspend fun updateGuest159(guest159: GuestEntity159)

    @Delete
    suspend fun deleteGuest159(guest159: GuestEntity159)

    @Query("SELECT * FROM tabel_guest_159 WHERE id_159 = :guestId159 AND user_id_159 = :userId159 LIMIT 1")
    suspend fun getGuestById159(guestId159: Int, userId159: Int): GuestEntity159?

    @Query("SELECT * FROM tabel_guest_159 WHERE user_id_159 = :userId159 ORDER BY timestamp_159 DESC")
    fun getGuestsByUser159(userId159: Int): Flow<List<GuestEntity159>>

    @Query("SELECT * FROM tabel_guest_159 WHERE user_id_159 = :userId159 ORDER BY timestamp_159 DESC")
    fun getAllGuestsByUser159(userId159: Int): Flow<List<GuestEntity159>>

    @Query("SELECT COUNT(*) FROM tabel_guest_159 WHERE user_id_159 = :userId159 AND timestamp_159 BETWEEN :startOfDay159 AND :endOfDay159")
    fun getDailyGuestCount159(userId159: Int, startOfDay159: Long, endOfDay159: Long): Flow<Int>

    @Query("SELECT COUNT(*) FROM tabel_guest_159 WHERE user_id_159 = :userId159")
    fun getTotalGuestCount159(userId159: Int): Flow<Int>

    // shortcut: SQLite LIKE substring matching for offline search, upgrade to Room FTS4/FTS5 when dataset exceeds 10,000 records.
    @Query("""
        SELECT * FROM tabel_guest_159 
        WHERE user_id_159 = :userId159 
        AND (nama_159 LIKE '%' || :keyword159 || '%' 
             OR keperluan_159 LIKE '%' || :keyword159 || '%' 
             OR alamat_159 LIKE '%' || :keyword159 || '%') 
        ORDER BY timestamp_159 DESC
    """)
    fun searchGuests159(userId159: Int, keyword159: String): Flow<List<GuestEntity159>>

    @Query("""
        SELECT * FROM tabel_guest_159 
        WHERE user_id_159 = :userId159 
        AND (nama_159 LIKE '%' || :keyword159 || '%' 
             OR keperluan_159 LIKE '%' || :keyword159 || '%' 
             OR alamat_159 LIKE '%' || :keyword159 || '%') 
        ORDER BY timestamp_159 DESC
    """)
    fun searchGuestsByUser159(userId159: Int, keyword159: String): Flow<List<GuestEntity159>>
}
