package com.example.easycalc

interface CardListener {
    fun onCardClicked(co: CalcOperation)
    fun onHistoryCleared(msg: String)
}