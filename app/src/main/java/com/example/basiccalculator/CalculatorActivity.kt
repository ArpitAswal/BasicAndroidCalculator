package com.example.basiccalculator

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.ColorFilter
import android.graphics.PorterDuff
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.text.isDigitsOnly
import java.util.Stack
import kotlin.math.roundToInt

class CalculatorActivity : AppCompatActivity() {

    private lateinit var cal: TextView
    private lateinit var res: TextView
    private lateinit var b1: ImageButton
    private lateinit var b2: ImageButton
    private lateinit var b3: ImageButton
    private lateinit var b4: ImageButton
    private lateinit var b5: ImageButton
    private lateinit var b6: ImageButton
    private lateinit var b7: ImageButton
    private lateinit var b8: ImageButton
    private lateinit var b9: ImageButton
    private lateinit var b0: ImageButton
    private lateinit var add: ImageButton
    private lateinit var minus: ImageButton
    private lateinit var multiple: ImageButton
    private lateinit var divisor: ImageButton
    private lateinit var mod: ImageButton
    private lateinit var clear: ImageButton
    private lateinit var cancel: ImageButton
    private lateinit var equal: ImageButton
    private lateinit var dot: ImageButton
    private lateinit var light: ImageButton
    private lateinit var dark: ImageButton
    private lateinit var layout: ConstraintLayout
    private lateinit var postfix: List<String>

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculator)
        init()
        layout.setBackgroundResource(R.color.white)
        light.setOnClickListener {
            layout.setBackgroundResource(R.color.white)
            cal.setTextColor(Color.parseColor("#000000"))
            res.setTextColor(Color.parseColor("#000000"))
            b0.setBackgroundResource(R.drawable.white_curve_square)
            b0.setColorFilter(Color.parseColor("#000000"))
            b1.setBackgroundResource(R.drawable.white_curve_square)
            b1.setColorFilter(Color.parseColor("#000000"))
            b2.setBackgroundResource(R.drawable.white_curve_square)
            b2.setColorFilter(Color.parseColor("#000000"))
            b3.setBackgroundResource(R.drawable.white_curve_square)
            b3.setColorFilter(Color.parseColor("#000000"))
            b4.setBackgroundResource(R.drawable.white_curve_square)
            b4.setColorFilter(Color.parseColor("#000000"))
            b5.setBackgroundResource(R.drawable.white_curve_square)
            b5.setColorFilter(Color.parseColor("#000000"))
            b6.setBackgroundResource(R.drawable.white_curve_square)
            b6.setColorFilter(Color.parseColor("#000000"))
            b7.setBackgroundResource(R.drawable.white_curve_square)
            b7.setColorFilter(Color.parseColor("#000000"))
            b8.setBackgroundResource(R.drawable.white_curve_square)
            b8.setColorFilter(Color.parseColor("#000000"))
            b9.setBackgroundResource(R.drawable.white_curve_square)
            b9.setColorFilter(Color.parseColor("#000000"))
            clear.setBackgroundResource(R.drawable.white_curve_square)
            cancel.setBackgroundResource(R.drawable.white_curve_square)
            mod.setBackgroundResource(R.drawable.white_curve_square)
            dot.setBackgroundResource(R.drawable.white_curve_square)
            dot.setColorFilter(Color.parseColor("#000000"))
        }
        dark.setOnClickListener {
            layout.setBackgroundResource(R.color.black)
            cal.setTextColor(Color.parseColor("#FFFFFF"))
            res.setTextColor(Color.parseColor("#FFFFFF"))
            b0.setBackgroundResource(R.drawable.black_curve_square)
            b0.setColorFilter(Color.parseColor("#FFFFFF"))
            b1.setBackgroundResource(R.drawable.black_curve_square)
            b1.setColorFilter(Color.parseColor("#FFFFFF"))
            b2.setBackgroundResource(R.drawable.black_curve_square)
            b2.setColorFilter(Color.parseColor("#FFFFFF"))
            b3.setBackgroundResource(R.drawable.black_curve_square)
            b3.setColorFilter(Color.parseColor("#FFFFFF"))
            b4.setBackgroundResource(R.drawable.black_curve_square)
            b4.setColorFilter(Color.parseColor("#FFFFFF"))
            b5.setBackgroundResource(R.drawable.black_curve_square)
            b5.setColorFilter(Color.parseColor("#FFFFFF"))
            b6.setBackgroundResource(R.drawable.black_curve_square)
            b6.setColorFilter(Color.parseColor("#FFFFFF"))
            b7.setBackgroundResource(R.drawable.black_curve_square)
            b7.setColorFilter(Color.parseColor("#FFFFFF"))
            b8.setBackgroundResource(R.drawable.black_curve_square)
            b8.setColorFilter(Color.parseColor("#FFFFFF"))
            b9.setBackgroundResource(R.drawable.black_curve_square)
            b9.setColorFilter(Color.parseColor("#FFFFFF"))
            clear.setBackgroundResource(R.drawable.black_curve_square)
            cancel.setBackgroundResource(R.drawable.black_curve_square)
            mod.setBackgroundResource(R.drawable.black_curve_square)
            dot.setBackgroundResource(R.drawable.black_curve_square)
            dot.setColorFilter(Color.parseColor("#FFFFFF"))
        }

        cal.addTextChangedListener(object: TextWatcher{
            var prev = ""
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                var string = s.toString()
                if(string.length>2 && string.subSequence(0,string.length-1) == prev && !prev.last().isDigit() && !string.last().isDigit()) {
                    string = "${string.subSequence(0, string.length - 2)}" + "${string.last()}"
                    prev = string
                    cal.text = string
                } else {
                    prev = string.subSequence(0,string.length).toString()
                }
            }

            override fun afterTextChanged(s: Editable?) {
                var tokens = tokenize(s.toString())
                if(tokens.isNotEmpty() && tokens.last() == "."){
                    tokens = tokens.subList(0,tokens.size-1)
                }
                postfix = if(tokens.all { char-> char.isDigitsOnly() }){
                    tokens
                } else {
                    infixToPostfix(tokens)
                }
            }

        })

        b1.setOnClickListener {
            if(res.visibility == View.VISIBLE)
                clear(it)
            cal.text = "${cal.text}" + "1"
        }
        b2.setOnClickListener {
            if(res.visibility == View.VISIBLE)
                clear(it)
            cal.text = "${cal.text}" + "2"
        }
        b3.setOnClickListener {
            if(res.visibility == View.VISIBLE)
                clear(it)
            cal.text = "${cal.text}" + "3"
        }
        b4.setOnClickListener {
            if(res.visibility == View.VISIBLE)
                clear(it)
            cal.text = "${cal.text}" + "4"
        }
        b5.setOnClickListener {
            if(res.visibility == View.VISIBLE)
                clear(it)
            cal.text = "${cal.text}" + "5"
        }
        b6.setOnClickListener {
            if(res.visibility == View.VISIBLE)
                clear(it)
            cal.text = "${cal.text}" + "6"
        }
        b7.setOnClickListener {
            if(res.visibility == View.VISIBLE)
                clear(it)
            cal.text = "${cal.text}" + "7"
        }
        b8.setOnClickListener {
            if(res.visibility == View.VISIBLE)
                clear(it)
            cal.text = "${cal.text}" + "8"
        }
        b9.setOnClickListener {
            if(res.visibility == View.VISIBLE)
                clear(it)
            cal.text = "${cal.text}" + "9"
        }
        b0.setOnClickListener {
            if(res.visibility == View.VISIBLE)
                clear(it)
            cal.text = "${cal.text}" + "0"
        }
        clear.setOnClickListener {
            clear(it)
        }
        add.setOnClickListener {
            if(res.visibility == View.VISIBLE) {
                cal.text = "${res.text}" + "+"
                res.text = ""
                res.visibility = View.GONE
            }
            else
                cal.text = "${cal.text}" + "+"
        }
        minus.setOnClickListener {
            if(res.visibility == View.VISIBLE) {
                cal.text = "${res.text}" + "-"
                res.text = ""
                res.visibility = View.GONE
            }
            else
                cal.text = "${cal.text}" + "-"
        }
        multiple.setOnClickListener {
            if(res.visibility == View.VISIBLE) {
                cal.text = "${res.text}" + "*"
                res.text = ""
                res.visibility = View.GONE
            }
            else
                cal.text = "${cal.text}" + "*"
        }
        divisor.setOnClickListener {
            if(res.visibility == View.VISIBLE) {
                cal.text = "${res.text}" + "/"
                res.text = ""
                res.visibility = View.GONE
            }
            else
            cal.text = "${cal.text}" + "/"
        }
        cancel.setOnClickListener {
            if(res.visibility == View.GONE && cal.text.isNotEmpty()) {
                val string = cal.text.toString()
                cal.text = string.subSequence(0, string.length - 1)
            }
        }
        mod.setOnClickListener {
            cal.text = "${cal.text}" + "%"
        }
        equal.setOnClickListener{
            if(postfix.all { char-> char.isDigitsOnly() }) {
                res.visibility = View.VISIBLE
                res.text = "=  $postfix"
            } else {
                var ans = evaluatePostfix(postfix).toString()
                if (!ans.isNullOrEmpty()) {
                    val value = ans
                    if(value.last() == '0' && value.elementAt(value.length-2) == '.')
                        ans = "${value.subSequence(0,value.length-2)}"
                    res.visibility = View.VISIBLE
                    res.text = "=  $ans"
                }
            }
        }
        dot.setOnClickListener {
            val text = cal.text
            if(text.isEmpty())
              cal.text = "0."
            else if(text.isNotEmpty()){
                if(text.last().isDigit())
                    cal.text = "$text."
                else
                    cal.text = "$text"+"0."
            }
        }
    }

    fun clear(view: View){
        cal.text = ""
        res.text = ""
        res.visibility = View.GONE
    }

    private fun init() {
        cal = findViewById(R.id.calculationTV)
        res = findViewById(R.id.resultTV)
        b1 = findViewById(R.id.oneBtn)
        b2 = findViewById(R.id.twoBtn)
        b3 = findViewById(R.id.threeBtn)
        b4 = findViewById(R.id.fourBtn)
        b5 = findViewById(R.id.fiveBtn)
        b6 = findViewById(R.id.sixBtn)
        b7 = findViewById(R.id.sevenBtn)
        b8 = findViewById(R.id.eigthBtn)
        b9 = findViewById(R.id.nineBtn)
        b0 = findViewById(R.id.zeroBtn)
        add = findViewById(R.id.plusBtn)
        minus = findViewById(R.id.minusBtn)
        multiple = findViewById(R.id.multipleBtn)
        divisor = findViewById(R.id.divisionBtn)
        cancel = findViewById(R.id.cancelBtn)
        clear = findViewById(R.id.clearBtn)
        mod = findViewById(R.id.modBtn)
        equal = findViewById(R.id.equalBtn)
        dot = findViewById(R.id.dotBtn)
        light = findViewById(R.id.lightBtn)
        dark = findViewById(R.id.darkBtn)
        layout = findViewById(R.id.constraintLayout)
    }

    private fun tokenize(expression: String): List<String> {
        val regex = """(\d+|\+|\-|\*|\/|\%|\.)""".toRegex()
        return regex.findAll(expression).map { it.value }.toList()
    }

    private fun infixToPostfix(tokens: List<String>): List<String> {
        val output = mutableListOf<String>()
        val operators = Stack<String>()

        for (token in tokens) {
            when (token) {
                // Operand (number)
                in "0".."9" ->
                    output.add(token)
                "." -> output.add(token)
                // Left parenthesis
                "(" -> operators.push(token)
                // Right parenthesis
                ")" -> {
                    while (operators.peek() != "(" && operators.isNotEmpty()) {
                        output.add(operators.pop())
                    }
                    operators.pop() // Remove the "("
                }
                // Operator (+, -, *, /)
                "+","-","*","/","%" -> {
                    while (operators.isNotEmpty() && precedence(operators.peek()) >= precedence(token)) {
                        output.add(operators.pop())
                    }
                    operators.push(token)
                }
                else -> {
                 output.add(token)
                }
            }
        }

        // Add remaining operators from the stack
        while (operators.isNotEmpty()) {
            output.add(operators.pop())
        }

        return output
    }

    private fun precedence(operator: String): Int {
        return when (operator) {
            "%" -> 3
            "*", "/" -> 2
            "+", "-" -> 1
            else -> 0
        }
    }

    private fun evaluatePostfix(postfix: List<String>): Float? {
       try {
           val stack = Stack<String>()

           for (token in postfix) {
               when (token) {
                   "." -> stack.push(token)
                   "+", "-", "*", "/" -> { // Operator (+, -, *, /)
                       val operand2 = stack.pop().toFloat()
                       if(stack.isEmpty())
                           return operand2
                       val operand1 = stack.pop().toFloat()
                       val result = when (token) {
                           "+" -> operand1 + operand2
                           "-" -> operand1 - operand2
                           "*" -> operand1 * operand2
                           "/" -> operand1 / operand2
                           "%" -> operand1 % operand2
                           else -> throw IllegalArgumentException("Invalid operator: $token")
                       }
                       stack.push(
                           String.format(
                               "%.3f",
                               result
                           )
                       )  // Push result as string (might have decimals)
                   }
                   else -> {  // Handle integers directly as strings
                       if (stack.isNotEmpty() && stack.peek() == ".") {
                           var combine = ""
                           val dot = stack.pop()
                           combine = if (stack.isNotEmpty() && !singleDotDigit(stack.peek())) {
                               val operand = stack.pop()
                               "$operand$dot$token"
                           } else {
                               "$dot$token"
                           }
                           stack.push(combine)
                       } else {
                           stack.push(token)
                       }
                   }
               }
           }

           return stack.pop().toFloat()
       } catch(e: Exception){
           Toast.makeText(this,e.message.toString(), Toast.LENGTH_LONG).show()
           return null
       }
    }

    private fun singleDotDigit(digit: String): Boolean {
        return digit.isNotEmpty() && digit.length >= 3 && !digit.all { char -> char.isDigit()}
    }
}
