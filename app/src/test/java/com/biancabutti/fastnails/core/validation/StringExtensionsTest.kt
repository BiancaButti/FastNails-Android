package com.biancabutti.fastnails.core.validation

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class StringExtensionsTest {

    // isValidEmail

    @Test
    fun validEmailReturnsTrue() {
        assertTrue("user@example.com".isValidEmail)
    }

    @Test
    fun emailWithSubdomainReturnsTrue() {
        assertTrue("user@mail.example.com".isValidEmail)
    }

    @Test
    fun emailWithPlusAliasReturnsTrue() {
        assertTrue("user+tag@example.com".isValidEmail)
    }

    @Test
    fun emailWithoutAtSignReturnsFalse() {
        assertFalse("userexample.com".isValidEmail)
    }

    @Test
    fun emailWithoutDomainReturnsFalse() {
        assertFalse("user@".isValidEmail)
    }

    @Test
    fun emailWithoutTldReturnsFalse() {
        assertFalse("user@example".isValidEmail)
    }

    @Test
    fun emptyEmailReturnsFalse() {
        assertFalse("".isValidEmail)
    }

    // isValidPassword

    @Test
    fun passwordWithExactlySixCharsReturnsTrue() {
        assertTrue("123456".isValidPassword)
    }

    @Test
    fun passwordLongerThanSixCharsReturnsTrue() {
        assertTrue("password123".isValidPassword)
    }

    @Test
    fun passwordWithFiveCharsReturnsFalse() {
        assertFalse("12345".isValidPassword)
    }

    @Test
    fun emptyPasswordReturnsFalse() {
        assertFalse("".isValidPassword)
    }
}
