package com.uts.tamuin166.utils

import android.content.Context
import android.content.SharedPreferences

class SessionManager166(context166: Context) {

    private val preferences166: SharedPreferences =
        context166.getSharedPreferences(PREF_NAME_166, Context.MODE_PRIVATE)

    companion object {
        // shortcut: plain SharedPreferences storage for 100% offline student UTS assignment, upgrade to EncryptedSharedPreferences for enterprise production apps.
        private const val PREF_NAME_166 = "sesi_tamuin_166"
        private const val KEY_USER_ID_166 = "user_id_166"
        private const val KEY_USERNAME_166 = "username_166"
    }

    fun saveSession166(userId166: Int, username166: String) {
        val editor166 = preferences166.edit()
        editor166.putInt(KEY_USER_ID_166, userId166)
        editor166.putString(KEY_USERNAME_166, username166)
        editor166.apply()
    }

    fun getUserId166(): Int {
        return preferences166.getInt(KEY_USER_ID_166, -1)
    }

    fun getUsername166(): String {
        return preferences166.getString(KEY_USERNAME_166, "") ?: ""
    }

    fun isLoggedIn166(): Boolean {
        return getUserId166() != -1
    }

    fun clearSession166() {
        val editor166 = preferences166.edit()
        editor166.clear()
        editor166.apply()
    }
}
