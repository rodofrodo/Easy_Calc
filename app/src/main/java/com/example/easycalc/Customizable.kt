package com.example.easycalc

object Customizable {
    private var cardListeners = mutableListOf<CardListener>()

    fun addCardListener(cl: CardListener) {
        cardListeners.add(cl)
    }

    fun notifyCardListeners(co: CalcOperation) {
        for (cl in cardListeners)
            cl.onCardClicked(co)
    }

    fun notifyAboutClear(msg: String) {
        for (cl in cardListeners)
            cl.onHistoryCleared(msg)
    }

    fun getSymbol(op: Operator): String = when(op) {
        Operator.PLUS -> "+"
        Operator.MINUS -> "-"
        Operator.MULTIPLY -> "*"
        Operator.DIVIDE -> "/"
        else -> "?"
    }
}