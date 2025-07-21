package com.example.easycalc

fun Double.smartFormat(decimals: Int = 10): String {
    val rounded = "%.${decimals}f".format(this).toDouble()
    val final = if (kotlin.math.abs(this - rounded) < 1e-9)
        rounded
    else this
    return if (final % 1 == 0.0) final.toInt().toString() else final.toString()
}
