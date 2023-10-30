package com.swasi.utility.utils

import java.util.regex.Matcher
import java.util.regex.Pattern

fun String.extractSixDigits(): String? {
    val p: Pattern = Pattern.compile("(\\d{6})")
    val m: Matcher = p.matcher(this)
    return if (m.find()) {
        m.group(1)
    } else ""
}

fun String.extractEightDigits(): String? {
    val p: Pattern = Pattern.compile("(\\d{8})")
    val m: Matcher = p.matcher(this)
    return if (m.find()) {
        m.group(1)
    } else ""
}

fun String.extractFourDigits(): String? {
    val p: Pattern = Pattern.compile("(\\d{8})")
    val m: Matcher = p.matcher(this)
    return if (m.find()) {
        m.group(1)
    } else ""
}