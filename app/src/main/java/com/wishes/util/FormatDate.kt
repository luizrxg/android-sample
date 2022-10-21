package com.wishes.util

import java.time.LocalDateTime
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.*

fun formatDayNumberMonthName(data: LocalDateTime): String {
    return "${data.dayOfMonth} ${data.month.getDisplayName(TextStyle.SHORT, Locale.getDefault())}"
}

fun formatDayNumberMonthName(data: String): String {
    val dataF = LocalDateTime.parse(data)
    return "${dataF.dayOfMonth} ${dataF.month.getDisplayName(TextStyle.SHORT, Locale.getDefault())}"
}

fun formatDayMonthYearNumber(data: LocalDateTime): String {
    return "${data.dayOfMonth}${data.month}${data.year}"
}

fun formatDayMonthYearNumber(data: String): String {
    val dataF = LocalDateTime.parse(data)
    return "${dataF.dayOfMonth}${dataF.month}${dataF.year}"
}

fun formatDayMonthNumber(data: LocalDateTime): String {
    return "${data.dayOfMonth}${data.month}"
}

fun formatDayMonthNumber(data: String): String {
    val dataF = LocalDateTime.parse(data)
    return "${dataF.dayOfMonth}${dataF.month}"
}

fun formatHoursMinutes(data: LocalDateTime): String {
    return "${if (data.hour > 9) data.hour else "0${data.hour}"}:${if (data.minute > 9) data.minute else "0${data.minute}"}"
}

fun formatHoursMinutes(data: String): String {
    val dataF = LocalDateTime.parse(data)
    return "${if (dataF.hour > 9) dataF.hour else "0${dataF.hour}"}:${if (dataF.minute > 9) dataF.minute else "0${dataF.minute}"}"
}

enum class Week(
    val weekDay: LocalDateTime,
) {
    WEEK_START(LocalDateTime.now().minusDays(LocalDateTime.now().dayOfWeek.value.toLong())),
    WEEK_END(LocalDateTime.now().plusDays(6 - LocalDateTime.now().dayOfWeek.value.toLong()));
}

enum class Month(
    val monthDay: LocalDateTime,
) {
    MONTH_START(LocalDateTime.now().minusDays(LocalDateTime.now().dayOfMonth.toLong())),
    MONTH_END(LocalDateTime.now());
}