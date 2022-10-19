package com.wishes.util

fun isBigDecimal(
    newValue: String,
    setValue: (value: String) -> Unit
){
    var numeric = true
    try {
        newValue.toBigDecimal()
    } catch (e: NumberFormatException){
        numeric = false
    }
    if (numeric){
        setValue(newValue.ifEmpty { "0" })
    }
}