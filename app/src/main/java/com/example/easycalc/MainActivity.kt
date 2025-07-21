package com.example.easycalc

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.math.sqrt

class MainActivity : AppCompatActivity(), CardListener {
    // global control variables
    private lateinit var buttons: MutableMap<Int, Button>
    private lateinit var resultText: TextView
    private lateinit var clearBtn: Button
    private lateinit var plusMinusBtn: Button
    private lateinit var backBtn: Button
    private lateinit var divideBtn: Button
    private lateinit var multiplyBtn: Button
    private lateinit var minusBtn: Button
    private lateinit var plusBtn: Button
    private lateinit var equalsBtn: Button
    private lateinit var commaBtn: Button
    private lateinit var sqrtBtn: Button
    private lateinit var opLbl: TextView
    private lateinit var historyBtn: ImageView
    private lateinit var infoBtn: ImageView

    // overall variables
    private var firstNum = .0
    private var secondNum = .0
    private var operation = Operator.EMPTY
    private var inputType = Input.NOT_OP

    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // ---
        Customizable.addCardListener(this)
        // ---
        buttons = mutableMapOf()
        for (i in 0..9) {
            val btnName = "btn_$i"
            val resId = resources.getIdentifier(btnName, "id", packageName)
            if (resId != 0) {
                val button = findViewById<Button>(resId)
                button.setOnClickListener { onNumberClicked(i) }
                buttons[i] = button
            }
        }
        // ---
        resultText = findViewById(R.id.resultLbl)
        resultText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                resultText.requestLayout()
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
        opLbl = findViewById(R.id.opLbl)
        opLbl.text = ""
        // --
        clearBtn = findViewById(R.id.clearBtn)
        clearBtn.setOnClickListener { clearAllText() }
        // ---
        plusMinusBtn = findViewById(R.id.plusMinusBtn)
        plusMinusBtn.setOnClickListener { setSign() }
        // ---
        backBtn = findViewById(R.id.backBtn)
        backBtn.setOnClickListener { clearLastChar() }
        // ---
        equalsBtn = findViewById(R.id.equalsBtn)
        equalsBtn.setOnClickListener { solve() }
        // ---
        commaBtn = findViewById(R.id.commaBtn)
        commaBtn.setOnClickListener { insertComma() }
        // ---
        sqrtBtn = findViewById(R.id.sqrtBtn)
        sqrtBtn.setOnClickListener { sqrtCalc() }
        // ---
        divideBtn = findViewById(R.id.divideBtn)
        multiplyBtn = findViewById(R.id.multiplyBtn)
        minusBtn = findViewById(R.id.minusBtn)
        plusBtn = findViewById(R.id.plusBtn)
        divideBtn.setOnClickListener { onOpClicked(Operator.DIVIDE) }
        multiplyBtn.setOnClickListener { onOpClicked(Operator.MULTIPLY) }
        minusBtn.setOnClickListener { onOpClicked(Operator.MINUS) }
        plusBtn.setOnClickListener { onOpClicked(Operator.PLUS) }
        // ---
        historyBtn = findViewById(R.id.history_btn)
        infoBtn = findViewById(R.id.info_btn)
        historyBtn.setOnClickListener { moveToHistory() }
        infoBtn.setOnClickListener { showInfo() }
    }

    private fun onNumberClicked(number: Int) {
        if (resultText.text.toString() == "0" || inputType != Input.NOT_OP) {
            if (inputType == Input.EQUALS)
                opLbl.text = ""
            resultText.text = number.toString()
            inputType = Input.NOT_OP
        }
        else
            resultText.append(number.toString())
    }

    private fun clearAllText() {
        resultText.text = "0"
        opLbl.text = ""
    }

    private fun setSign() {
        var text = resultText.text.toString()
        if (text == "0")
            return
        if (text[0] == '-')
            resultText.text = text.drop(1)
        else {
            text = "-$text"
            resultText.text = text
        }
    }

    private fun clearLastChar() {
        var text = resultText.text.toString().dropLast(1)
        if (text == "" || text == "-")
            text = "0"
        resultText.text = text
        if (inputType == Input.EQUALS) {
            inputType = Input.NOT_OP
            opLbl.text = ""
        }
    }

    private fun solve() {
        secondNum = resultText.text.toString().toDouble()
        val result = when (operation) {
            Operator.PLUS -> firstNum + secondNum
            Operator.MINUS -> firstNum - secondNum
            Operator.MULTIPLY -> firstNum * secondNum
            Operator.DIVIDE ->  firstNum / secondNum
            Operator.EMPTY -> TODO()
        }
        resultText.text = result.smartFormat()
        opLbl.text = getOpText(operation, firstNum, secondNum)
        inputType = Input.EQUALS
        val op = CalcOperation(operation, firstNum, secondNum, result)
        HistoryHolder.historyList.add(op)
    }

    private fun onOpClicked(op: Operator) {
        firstNum = resultText.text.toString().toDouble()
        operation = op
        opLbl.text = getOpText(op, firstNum)
        inputType = Input.OP
    }

    private fun getOpText(op: Operator, first: Double, second: Double? = null): String {
        var res = "${first.smartFormat()} "
        val char = when (op) {
            Operator.PLUS -> '+'
            Operator.MINUS -> '-'
            Operator.MULTIPLY -> '*'
            Operator.DIVIDE -> '/'
            Operator.EMPTY -> TODO()
        }
        res += char
        if (second != null)
            res += " ${second.smartFormat()} ="
        return res
    }

    private fun sqrtCalc() {
        val x = sqrt(resultText.text.toString().toDouble())
        resultText.text = x.smartFormat()
        if (inputType == Input.EQUALS) {
            inputType = Input.NOT_OP
            opLbl.text = ""
        }
    }

    private fun insertComma() {
        if (inputType != Input.NOT_OP) {
            opLbl.text = if (inputType == Input.EQUALS) "" else opLbl.text
            resultText.text = "0."
            inputType = Input.NOT_OP
        }
        else if (!resultText.text.contains('.'))
            resultText.append(".")
    }

    private fun moveToHistory() {
        val intent = Intent(this, HistoryPage::class.java)
        startActivity(intent)
    }

    private fun showInfo() {
        val intent = Intent(this, InfoPage::class.java)
        startActivity(intent)
    }

    override fun onCardClicked(co: CalcOperation) {
        opLbl.text = "${co.first.smartFormat()} ${Customizable.getSymbol(co.op)}" +
                " ${co.second.smartFormat()} ="
        resultText.text = co.result.smartFormat()
        inputType = Input.EQUALS
    }

    override fun onHistoryCleared(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}