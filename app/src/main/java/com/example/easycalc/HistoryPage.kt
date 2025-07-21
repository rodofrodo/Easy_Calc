package com.example.easycalc

import android.graphics.Typeface
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.graphics.toColorInt

class HistoryPage : AppCompatActivity() {
    private lateinit var historyContainer: LinearLayout
    private lateinit var clearHistoryBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_history_page)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        historyContainer = findViewById(R.id.historyContainer)
        for (co in HistoryHolder.historyList)
            updateHistory(co)
        // ---
        clearHistoryBtn = findViewById(R.id.clearHistoryBtn)
        clearHistoryBtn.setOnClickListener { clearHistory() }
        clearHistoryBtn.text = if (HistoryHolder.historyList.isEmpty()) "Back" else "Clear"
    }

    private fun updateHistory(co: CalcOperation) {
        val itemLayout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(24, 24, 24, 24)
            setBackgroundResource(R.drawable.history_background)
            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            params.setMargins(0, 0, 0, 24)
            layoutParams = params
        }
        val operationView = TextView(this).apply {
            text = "${co.first.smartFormat()} ${Customizable.getSymbol(co.op)} " +
                    "${co.second.smartFormat()}"
            textSize = 16f
            setTextColor("#b3b3b3".toColorInt())
        }
        val resultView = TextView(this).apply {
            text = "= ${co.result.smartFormat()}"
            textSize = 24f
            setTypeface(null, Typeface.BOLD)
            setTextColor("#ffffff".toColorInt())
        }
        itemLayout.addView(operationView)
        itemLayout.addView(resultView)
        itemLayout.setOnClickListener { onCardClicked(co) }
        historyContainer.addView(itemLayout, 0)
    }

    private fun onCardClicked(co: CalcOperation) {
        Customizable.notifyCardListeners(co)
        finish()
    }

    private fun clearHistory() {
        if (HistoryHolder.historyList.isNotEmpty()) {
            Customizable.notifyAboutClear("History has been cleared")
            HistoryHolder.historyList.clear()
        }
        finish()
    }
}