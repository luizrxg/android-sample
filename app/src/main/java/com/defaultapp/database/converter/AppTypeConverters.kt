package com.defaultapp.database.converter

import androidx.room.TypeConverter
import org.threeten.bp.format.DateTimeFormatter
import java.math.BigDecimal

object AppTypeConverters {
    private val formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME

    @TypeConverter
    @JvmStatic
    fun bigDecimalToDoubleinput(value: BigDecimal?): Double {
        return value?.toDouble() ?: 0.0
    }

    @TypeConverter
    @JvmStatic
    fun doubleToBigDecimal(input: Double?): BigDecimal {
        if (input == null) return BigDecimal.ZERO
        return BigDecimal.valueOf(input) ?: BigDecimal.ZERO
    }

    @TypeConverter
    @JvmStatic
    fun toOffsetDateTime(value: String?) = value?.let { formatter.parse(value, org.threeten.bp.LocalDateTime::from) }

    @TypeConverter
    @JvmStatic
    fun fromOffsetDateTime(date: org.threeten.bp.LocalDateTime?): String? = date?.format(formatter)

    @TypeConverter
    @JvmStatic
    fun stringFromListString(value: String?): List<String>? {
        return if(value?.isNotEmpty() == true) {
            value.split(";")
        } else { emptyList() }
    }

    @TypeConverter
    @JvmStatic
    fun listStringToString(date: List<String>?): String? {
        return if(date?.isNotEmpty() == true) {
            date.joinToString(";")
        } else { "" }
    }
}