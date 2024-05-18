package com.example.pj001_calculator

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.isDigitsOnly
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.pj001_calculator.databinding.ActivityMainBinding

private lateinit var binding: ActivityMainBinding
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
//        initial viewbinding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        fun selectNumber (btn: Button, txtEnter: TextView) {
            btn.setOnClickListener{
                var tempBtn = btn.getText().toString()
                var tempEnter = txtEnter.getText().toString()
                if(tempEnter == "" ) {
                    if(tempBtn.isDigitsOnly())
                        txtEnter.text = tempEnter.plus(tempBtn)
                    else
                        Toast.makeText(this, "wrong syntax!", Toast.LENGTH_SHORT).show()
                }

                else {
                    if(tempEnter.last().isDigit())
                        txtEnter.text = tempEnter.plus(tempBtn)
                    else if (tempBtn.isDigitsOnly())
                        txtEnter.text = tempEnter.plus(tempBtn)
                    else
                        Toast.makeText(this, "wrong syntax!", Toast.LENGTH_SHORT).show()
                }


            }

        }

        selectNumber(binding.btn1, binding.txtEnter)
        selectNumber(binding.btn2, binding.txtEnter)
        selectNumber(binding.btn3, binding.txtEnter)
        selectNumber(binding.btn4, binding.txtEnter)
        selectNumber(binding.btn5, binding.txtEnter)
        selectNumber(binding.btn6, binding.txtEnter)
        selectNumber(binding.btn7, binding.txtEnter)
        selectNumber(binding.btn8, binding.txtEnter)
        selectNumber(binding.btn9, binding.txtEnter)
        selectNumber(binding.btn0, binding.txtEnter)
        selectNumber(binding.btnDot, binding.txtEnter)
        selectNumber(binding.btnDivide, binding.txtEnter)
        selectNumber(binding.btnMultiply, binding.txtEnter)
        selectNumber(binding.btnMinus, binding.txtEnter)
        selectNumber(binding.btnPlus, binding.txtEnter)


        binding.btnClear.setOnClickListener{
            with(binding) {
                txtEnter.text = ""
                txtResult.text = ""
            }
//            Toast.makeText(this,"Clear!", Toast.LENGTH_SHORT).show()
        }

        binding.btnBackSpace.setOnClickListener{
            with(binding){
                if(txtEnter.text.length > 0) {
                    txtEnter.text = txtEnter.text.substring(0,txtEnter.text.length-1)
                }
            }
//            Toast.makeText(this,"BackSpace", Toast.LENGTH_SHORT).show()
        }

        fun EqualHandler (input: String):Double {
//            split string  : "16*5+32.13+16/4" => 16 * 5 + 32 . 13 + 16 / 4
            val tokens = input.split("(?<=[-+*/])|(?=[-+*/])".toRegex())
//                Toast.makeText(this,"${tokens.size}", Toast.LENGTH_SHORT).show()

//            creates a new instance of a mutable{can change} list containing elements of type String
//            create a separate array to charge "*/" before
            val arr2s = mutableListOf<String>()
            var i = 0

            // First pass to handle * and /
            while (i < tokens.size) {
                if (tokens[i] == "*") {
//                    take value left and right of "*"
                    val left = arr2s.removeAt(arr2s.size - 1).toDouble()
                    val right = tokens[++i].toDouble()
                    arr2s.add((left * right).toString())
                } else if (tokens[i] == "/") {
                    val left = arr2s.removeAt(arr2s.size - 1).toDouble()
                    val right = tokens[++i].toDouble()
                    arr2s.add((left / right).toString())
                } else {
                    arr2s.add(tokens[i])
                }
                i++
            }

            // Second pass to handle + and -
            var result = arr2s[0].toDouble()
            i = 1

            while (i < arr2s.size) {
                when (arr2s[i]) {
                    "+" -> result += arr2s[++i].toDouble()
                    "-" -> result -= arr2s[++i].toDouble()
                }
                i++
            }

            return result
        }

//        binding.txtEnter.text = "16*5+32.13+16/4"

        binding.btnEqual.setOnClickListener{
            var checkLastIsDigit = binding.txtEnter.text.last().isDigit()
            if (checkLastIsDigit) {
                var resultDouble = EqualHandler(binding.txtEnter.text.toString()).toDouble()
                var formatResult = String.format("%.2f", resultDouble)
                binding.txtResult.setText(formatResult.toString())
            }
            else
                Toast.makeText(this, "wrong syntax!", Toast.LENGTH_SHORT).show()
        }
    }





}

