package com.uts.tamuin159.utils

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class ValidationHelper159Test {

    @Test
    fun testUsernameValidation159() {
        assertTrue(ValidationHelper159.isValidUsername159("admin159"))
        assertFalse(ValidationHelper159.isValidUsername159(""))
        assertFalse(ValidationHelper159.isValidUsername159("   "))
    }

    @Test
    fun testPasswordValidation159() {
        assertTrue(ValidationHelper159.isValidPassword159("123456"))
        assertTrue(ValidationHelper159.isValidPassword159("secretPass159"))
        assertFalse(ValidationHelper159.isValidPassword159("12345"))
        assertFalse(ValidationHelper159.isValidPassword159(""))
    }

    @Test
    fun testPasswordsMatch159() {
        assertTrue(ValidationHelper159.doPasswordsMatch159("rahasia159", "rahasia159"))
        assertFalse(ValidationHelper159.doPasswordsMatch159("rahasia159", "beda159"))
    }

    @Test
    fun testGuestFieldValidation159() {
        assertTrue(ValidationHelper159.isValidGuestField159("Budi Pratama"))
        assertFalse(ValidationHelper159.isValidGuestField159(""))
        assertFalse(ValidationHelper159.isValidGuestField159("   "))
    }
}
