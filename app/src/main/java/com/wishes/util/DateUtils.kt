package com.wishes.util

import java.time.LocalDateTime
import java.time.format.TextStyle
import java.util.*

fun checkSameDay(data1: String, data2: String): Boolean{
    val data1F = formatDayMonthYearNumber(LocalDateTime.parse(data1))
    val data2F = formatDayMonthYearNumber(LocalDateTime.parse(data2))

    return data1F == data2F
}

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

fun formatYearMonth(data: LocalDateTime): String{
    return "${data.year}${data.month.value}"
}

fun formatYearMonth(data: String): String{
    val dataF = LocalDateTime.parse(data)
    return "${dataF.year}${dataF.month.value}"
}

fun getMonthYear(): String{
    val data = LocalDateTime.now()

    return "${data.month.value}-${data.year}"
}

fun getYearMonth(): String{
    val data = LocalDateTime.now()

    return "${data.year}-${data.month.value}"
}

fun getTodayOfMonth(): Int{
    return LocalDateTime.now().dayOfMonth
}

fun getMonth(): Int{
    return LocalDateTime.now().month.value
}

fun getMonthsBetween(
    data1: String,
    data2: String
): Int {
    val mes1 = data1.substring(5, 7).toInt()
    val mes2 = data2.substring(5, 7).toInt()
    val ano1 = data1.substring(0, 4).toInt()
    val ano2 = data2.substring(0, 4).toInt()

    return if (ano1 == ano2) {
        mes2 - mes1
    } else if (ano2 > ano1) {
        (((ano2 - ano1) * 12) - mes1) + mes2
    } else 0
}

fun hasMonthDayPassed(day: Int): Boolean{
    val data = LocalDateTime.now()

    return data.dayOfMonth <= day
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