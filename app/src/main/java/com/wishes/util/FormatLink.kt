package com.wishes.util

fun checkHttps(url: String): String{
    var link = url.replace(" ", "")

    if (url.startsWith("http://")){
        link = link.replace("http", "https")
    } else if (!url.startsWith("https://")) {
        link = "https://$link"
    }

    return link
}