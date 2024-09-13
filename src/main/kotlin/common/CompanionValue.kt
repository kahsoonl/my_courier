package org.example.common

const val EMPTY_STRING = ""

fun String.Companion.emptyValue() = EMPTY_STRING

fun Int.Companion.emptyValue() = 0

fun Float.Companion.emptyValue() = 0f

fun Double.Companion.emptyValue() = 0.0

fun Boolean.Companion.emptyValue() = false

fun Long.Companion.emptyValue() = 0L