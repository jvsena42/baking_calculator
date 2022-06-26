package com.bulletapps.candypricer.presentation.util

private const val ZERO = 0
private const val ZERO_DOUBLE = 0.0
private const val ZERO_FLOAT = 0f

fun Int?.orZero() = this ?: ZERO
fun Double?.orZero() = this ?: ZERO_DOUBLE
fun Float?.orZero() = this ?: ZERO_FLOAT