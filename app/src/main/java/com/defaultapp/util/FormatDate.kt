package com.defaultapp.util

import java.time.LocalDateTime
import java.time.format.TextStyle
import java.util.*

fun formatDayNumberMonthName(data: LocalDateTime? = null): String{
    return if (data != null){
        "${data.dayOfMonth} ${data.month.getDisplayName(TextStyle.SHORT, Locale.getDefault())}"
    } else ""
}

fun formatDayMonthYearNumber(data: LocalDateTime? = null): String{
    return if (data != null){
        "${data.dayOfMonth}${data.month}${data.year}"
    } else ""
}

fun formatHoursMinutes(data: LocalDateTime? = null): String{
    return if (data != null){
        "${if (data.hour > 9) data.hour else "0${data.hour}"}:${if (data.minute > 9) data.minute else "0${data.minute}"}"
    } else ""
}

enum class Week (
    val weekDay: LocalDateTime,
) {
    WEEK_START(LocalDateTime.now().minusDays(LocalDateTime.now().dayOfWeek.value.toLong())),
    WEEK_END(LocalDateTime.now().plusDays(6 - LocalDateTime.now().dayOfWeek.value.toLong()));
}