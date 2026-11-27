package com.uts.tamuin166.utils

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class ValidationHelper166Test {

    @Test
    fun testUsernameValidation166() {
        assertTrue(ValidationHelper166.isValidUsername166("admin166"))
        assertFalse(ValidationHelper166.isValidUsername166(""))
        assertFalse(ValidationHelper166.isValidUsername166("   "))
    }

    @Test
    fun testPasswordValidation166() {
        assertTrue(ValidationHelper166.isValidPassword166("123456"))
        assertTrue(ValidationHelper166.isValidPassword166("secretPass166"))
        assertFalse(ValidationHelper166.isValidPassword166("12345"))
        assertFalse(ValidationHelper166.isValidPassword166(""))
    }

    @Test
    fun testPasswordsMatch166() {
        assertTrue(ValidationHelper166.doPasswordsMatch166("rahasia166", "rahasia166"))
        assertFalse(ValidationHelper166.doPasswordsMatch166("rahasia166", "beda166"))
    }

    @Test
    fun testGuestFieldValidation166() {
        assertTrue(ValidationHelper166.isValidGuestField166("Budi Pratama"))
        assertFalse(ValidationHelper166.isValidGuestField166(""))
        assertFalse(ValidationHelper166.isValidGuestField166("   "))
    }
}
