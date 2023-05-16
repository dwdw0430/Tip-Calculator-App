package com.firstapp.app

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.SeekBar
import android.widget.TextView

private const val TAG = "MainActivity"
private const val INITIAL_TIP_PERCENT = 15
private const val INITIAL_NUMBER_PEOPLE = 1
private const val MIN = 1
class MainActivity : AppCompatActivity() {

    private lateinit var BaseAmount: EditText
    private lateinit var seekBarTip: SeekBar
    private lateinit var PercentLabel: TextView
    private lateinit var TipAmount: TextView
    private lateinit var TotalAmount: TextView
    private lateinit var SplitNumber: TextView
    private lateinit var seekBarPeople: SeekBar
    private lateinit var AmountPerPerson: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        BaseAmount = findViewById(R.id.BaseAmount)
        seekBarTip = findViewById(R.id.seekBarTip)
        PercentLabel = findViewById(R.id.PercentLabel)
        TipAmount = findViewById(R.id.TipAmount)
        TotalAmount = findViewById(R.id.TotalAmount)

        SplitNumber = findViewById(R.id.SplitNumber)
        seekBarPeople = findViewById(R.id.seekBarPeople)
        AmountPerPerson = findViewById(R.id.AmountPerPerson)


        seekBarTip.progress = INITIAL_TIP_PERCENT
        PercentLabel.text = "$INITIAL_TIP_PERCENT%"
        seekBarPeople.progress = INITIAL_NUMBER_PEOPLE
        SplitNumber.text = "$INITIAL_NUMBER_PEOPLE"
        seekBarTip.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                Log.i(TAG, "onProgressChanged $progress")
                PercentLabel.text = "$progress%"
                computeTipAndTotal()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }

        })
        BaseAmount.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                Log.i(TAG, "afterTextChanged $s")
                computeTipAndTotal()
            }

        })

        seekBarPeople.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                Log.i(TAG, "onProgressChanged $progress")
                SplitNumber.text = "$progress"
                computeTotalPerPerson()
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }

        })
    }

    private fun computeTotalPerPerson() {
        if (TotalAmount.text.isEmpty()) {
            AmountPerPerson.text = ""
            return
        }
        val totalAmount = TotalAmount.text.toString().toDouble()
        val totalPeople = seekBarPeople.progress
        val totalPerPerson = totalAmount / totalPeople

        AmountPerPerson.text = "%.2f".format(totalPerPerson)
    }

    private fun computeTipAndTotal() {
        if (BaseAmount.text.isEmpty()) {
            TipAmount.text = ""
            TotalAmount.text = ""
            return
        }
        val baseAmount = BaseAmount.text.toString().toDouble()
        val tipPercent = seekBarTip.progress

        val tipAmount = baseAmount * tipPercent / 100
        val totalAmount = baseAmount + tipAmount

        TipAmount.text = "%.2f".format(tipAmount)
        TotalAmount.text = "%.2f".format(totalAmount)
    }
}