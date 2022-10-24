package com.wishes.util


fun formatDotToPeriod(string: String): String {
    var newString = string.replace(".", ",")

    if (newString.length > 2 && newString.dropLast(1).last() == ','){
        newString += "0"
    }

    return newString
}