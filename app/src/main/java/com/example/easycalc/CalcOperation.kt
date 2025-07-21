package com.example.easycalc

import java.io.Serializable

data class CalcOperation(
    val op: Operator,
    val first: Double,
    val second: Double,
    val result: Double
)
