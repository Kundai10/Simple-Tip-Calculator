package com.example.tippie

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.SeekBar
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import kotlin.math.ceil
import kotlin.math.floor

private const val firstTipPercentage = 15



class MainActivity : AppCompatActivity() {
    private lateinit var seekbarTip: SeekBar
    private lateinit var tvTipAmount: TextView
    private lateinit var calculatedTotal: TextView
    private lateinit var calculatedTip: TextView
    private lateinit var inputBillAmount: EditText
    private lateinit var tvTipPercent: TextView
    private lateinit var inputPeople: EditText
    private lateinit var buttonRoundUp: Button
    private lateinit var buttonRoundDown: Button
    private lateinit var calcPerPerson: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        seekbarTip = findViewById(R.id.seekbarTip)
        tvTipAmount = findViewById(R.id.tvTipAmount)
        calculatedTotal = findViewById(R.id.calculatedTotal)
        calculatedTip = findViewById(R.id.calculatedTip)
        inputBillAmount = findViewById(R.id.inputBillAmount)
        tvTipPercent = findViewById(R.id.tvTipPercent)
        inputPeople = findViewById(R.id.inputPeople)
        buttonRoundUp = findViewById(R.id.buttonRoundUp)
        buttonRoundDown = findViewById(R.id.buttonRoundDown)
        calcPerPerson = findViewById(R.id.calcPerPerson)

        seekbarTip.progress = firstTipPercentage
        tvTipPercent.text = "$firstTipPercentage%"
        inputPeople.setText("1", TextView.BufferType.EDITABLE)

        seekbarTip.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                tvTipPercent.text = "$p1%"
                calcTipAndTotal()
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {}

            override fun onStopTrackingTouch(p0: SeekBar?) {}

        })
        inputBillAmount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                calcTipAndTotal()
            }

        })
        inputPeople.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                calcTipAndTotal()
            }

        })
    }

    private fun calcTipAndTotal() {
        if (inputBillAmount.text.isEmpty() || inputPeople.text.isEmpty()) {
            calculatedTip.text = ""
            calculatedTotal.text = ""
            calcPerPerson.text = ""


        } else {
            // Get the value of the bill amount and tip percent that the user input
            val billAmount = inputBillAmount.text.toString().toDouble()
            val tipPercentage = seekbarTip.progress
            val numPeople = inputPeople.text.toString().toInt()

            // Calculate the tip and total and amount each person pays
            val tipAmount = billAmount * tipPercentage / 100
            val totalAmount = billAmount + tipAmount
            val eachPersonPays = tipAmount / numPeople
            // round the values to two decimal places
            calcPerPerson.text = "%.2f".format(eachPersonPays)
            calculatedTip.text = "%.2f".format(tipAmount)
            calculatedTotal.text = "%.2f".format(totalAmount)
            buttonRoundUp.setOnClickListener {
                calcPerPerson.text = ceil(eachPersonPays).toString()
                calculatedTip.text = ceil(tipAmount).toString()
                calculatedTotal.text = ceil(totalAmount).toString()
            }
            buttonRoundDown.setOnClickListener {
                calcPerPerson.text = floor(eachPersonPays).toString()
                calculatedTip.text = floor(tipAmount).toString()
                calculatedTotal.text = floor(totalAmount).toString()

            }
        }

        //buttonRoundUp.setOnClickListener(){ ceil(calculatedTip.text) }
        //buttonRoundDown.setOnClickListener { floor(tipAmount) }


    }

}
