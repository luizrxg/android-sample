package com.wishes.util

import java.time.LocalDateTime

fun checkSameDay(data1: String, data2: String): Boolean{
    val data1F = formatDayMonthYearNumber(LocalDateTime.parse(data1))
    val data2F = formatDayMonthYearNumber(LocalDateTime.parse(data2))

    return data1F == data2F
}