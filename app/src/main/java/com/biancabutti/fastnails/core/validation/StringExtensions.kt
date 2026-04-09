package com.biancabutti.fastnails.core.validation

import android.util.Patterns

val String.isValidEmail: Boolean
    get() = isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()

val String.isValidPassword: Boolean
    get() = length >= 6
