package com.wishes.util

import java.math.BigDecimal

fun formatDotToPeriod(string: String): String {
    var newString = string.replace(".", ",")

    if (newString.length > 2 && newString.dropLast(1).last() == ','){
        newString += "0"
    }

    return newString
}

fun formatToFixed2(value: BigDecimal): String{
    val string = value.toString()
    println(string)

    return if (
        string.length > 3 &&
       (string.dropLast(2).last() == ',' ||
        string.dropLast(2).last() == '.')
    ) {
        string
    } else if (
        string.length > 2 &&
       (string.dropLast(1).last() == ',' ||
        string.dropLast(1).last() == '.')
    ) {
        "${string}0"
    } else {
        "${string}.00"
    }
}