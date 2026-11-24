package com.uts.tamuin159.utils

import android.content.Context
import android.content.SharedPreferences

class SessionManager159(context159: Context) {

    private val preferences159: SharedPreferences =
        context159.getSharedPreferences(PREF_NAME_159, Context.MODE_PRIVATE)

    companion object {
        // shortcut: plain SharedPreferences storage for 100% offline student UTS assignment, upgrade to EncryptedSharedPreferences for enterprise production apps.
        private const val PREF_NAME_159 = "sesi_tamuin_159"
        private const val KEY_USER_ID_159 = "user_id_159"
        private const val KEY_USERNAME_159 = "username_159"
    }

    fun saveSession159(userId159: Int, username159: String) {
        val editor159 = preferences159.edit()
        editor159.putInt(KEY_USER_ID_159, userId159)
        editor159.putString(KEY_USERNAME_159, username159)
        editor159.apply()
    }

    fun getUserId159(): Int {
        return preferences159.getInt(KEY_USER_ID_159, -1)
    }

    fun getUsername159(): String {
        return preferences159.getString(KEY_USERNAME_159, "") ?: ""
    }

    fun isLoggedIn159(): Boolean {
        return getUserId159() != -1
    }

    fun clearSession159() {
        val editor159 = preferences159.edit()
        editor159.clear()
        editor159.apply()
    }
}
