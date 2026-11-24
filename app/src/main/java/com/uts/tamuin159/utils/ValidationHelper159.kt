package com.uts.tamuin159.utils

object ValidationHelper159 {

    fun isValidUsername159(username159: String): Boolean {
        return username159.trim().isNotEmpty()
    }

    fun isValidPassword159(password159: String): Boolean {
        return password159.trim().length >= 6
    }

    fun doPasswordsMatch159(password159: String, confirmPassword159: String): Boolean {
        return password159 == confirmPassword159
    }

    fun isValidGuestField159(value159: String): Boolean {
        return value159.trim().isNotEmpty()
    }
}
