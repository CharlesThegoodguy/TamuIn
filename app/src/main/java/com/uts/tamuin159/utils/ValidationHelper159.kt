package com.uts.tamuin166.utils

object ValidationHelper166 {

    fun isValidUsername166(username166: String): Boolean {
        return username166.trim().isNotEmpty()
    }

    fun isValidPassword166(password166: String): Boolean {
        return password166.trim().length >= 6
    }

    fun doPasswordsMatch166(password166: String, confirmPassword166: String): Boolean {
        return password166 == confirmPassword166
    }

    fun isValidGuestField166(value166: String): Boolean {
        return value166.trim().isNotEmpty()
    }
}
