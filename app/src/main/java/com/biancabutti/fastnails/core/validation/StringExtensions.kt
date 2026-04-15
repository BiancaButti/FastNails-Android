package com.biancabutti.fastnails.core.validation

private val EMAIL_REGEX = Regex("^[A-Za-z0-9._%+\\-]+@[A-Za-z0-9.\\-]+\\.[A-Za-z]{2,}$")

val String.isValidEmail: Boolean
    get() = isNotEmpty() && EMAIL_REGEX.matches(this)

val String.isValidPassword: Boolean
    get() = length >= 6
