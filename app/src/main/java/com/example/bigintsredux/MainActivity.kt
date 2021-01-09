package com.example.bigintsredux

import android.content.ClipData
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import android.content.ClipboardManager
import android.content.Context
import android.text.method.ScrollingMovementMethod
import java.math.BigInteger

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val calculateButton: Button = findViewById(R.id.calculateButton)
        val operatorSpinner: Spinner = findViewById(R.id.operatorSpinner)
        var firstField: EditText = findViewById(R.id.numberField1)
        var secondField: EditText = findViewById(R.id.numberField2)
        val clearButton: Button = findViewById(R.id.clearButton)
        val firstCopyButton: Button = findViewById(R.id.copyToField1Button)
        val secondCopyButton: Button = findViewById(R.id.copyToField2Button)
        val copySolutionButton: Button = findViewById(R.id.copySolutionTextButton)
        var solution: TextView = findViewById(R.id.solutionText)

        var clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

        solution.movementMethod = ScrollingMovementMethod()

        ArrayAdapter.createFromResource(
                this,
                R.array.operator_array,
                R.layout.spinner_layout
        ) .also {adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            operatorSpinner.adapter = adapter
        }

        calculateButton.setOnClickListener {
            var firstNumber: String = firstField.text.toString().trim()
            var secondNumber: String = secondField.text.toString().trim()
            var numericCheck1: Boolean
            var numericCheck2: Boolean
            //Regexs to check if a number is numerical or not
            numericCheck1 = firstNumber.matches("-?\\d+".toRegex())
            numericCheck2 = secondNumber.matches("-?\\d+".toRegex())
            if(firstNumber.isEmpty() || !numericCheck1){
                Toast.makeText(applicationContext, "Please enter a valid integer in the first field!", Toast.LENGTH_SHORT).show()
            }
            if(secondNumber.isEmpty() || !numericCheck2){
                Toast.makeText(applicationContext, "Please enter a valid integer in the second field!", Toast.LENGTH_SHORT).show()
            }
            if(numericCheck1 && numericCheck2){
                when {
                    operatorSpinner.selectedItem.toString().equals("+") -> {
                        solution.text = add(firstNumber, secondNumber)
                    }
                    operatorSpinner.selectedItem.toString().equals("-") -> {
                        solution.text = subtract(firstNumber, secondNumber)
                    }
                    else -> {
                        solution.text = multiply(firstNumber, secondNumber)
                    }
                }
            }
        }

        clearButton.setOnClickListener {
            firstField.text.clear()
            secondField.text.clear()
            solution.text = ""
        }

        firstCopyButton.setOnClickListener {
            if(solution.text.isEmpty()){
                Toast.makeText(applicationContext, "No solution to copy to first field!", Toast.LENGTH_SHORT).show()
            }
            else{
                firstField.setText(solution.text)
                Toast.makeText(applicationContext, "Solution copied to first field", Toast.LENGTH_SHORT).show()
            }
        }

        secondCopyButton.setOnClickListener {
            if(solution.text.isEmpty()){
                Toast.makeText(applicationContext, "No solution to copy to second field!", Toast.LENGTH_SHORT).show()
            }
            else{
                secondField.setText(solution.text)
                Toast.makeText(applicationContext, "Solution copied to second field", Toast.LENGTH_SHORT).show()
            }
        }

        copySolutionButton.setOnClickListener {
            if(solution.text.isEmpty()){
                Toast.makeText(applicationContext, "No solution to copy to clipboard!", Toast.LENGTH_SHORT).show()
            }
            else{
                var copiedSolution = ClipData.newPlainText("Solution to copy", solution.text)
                clipboard.setPrimaryClip(copiedSolution)
                Toast.makeText(applicationContext, "Solution copied to clipboard", Toast.LENGTH_SHORT).show()
            }
        }

    }


    fun add(firstNumber: String, secondNumber: String): String {
        var solution: BigInteger = BigInteger(firstNumber) + BigInteger(secondNumber)
        return solution.toString()
    }

    fun subtract(firstNumber: String, secondNumber: String): String {
        var solution: BigInteger = BigInteger(firstNumber) - BigInteger(secondNumber)
        return solution.toString()
    }

    fun multiply(firstNumber: String, secondNumber: String): String {
        var solution: BigInteger = BigInteger(firstNumber) * BigInteger(secondNumber)
        return solution.toString()
    }




}
