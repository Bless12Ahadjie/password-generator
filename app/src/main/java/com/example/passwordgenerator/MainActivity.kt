package com.example.passwordgenerator

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

//        Handling the seekbar
        val numberTextView = findViewById<TextView>(R.id.numberTextView)
        val seekBar = findViewById<SeekBar>(R.id.seekBar)

        seekBar.progress = 4
        numberTextView.text = seekBar.progress.toString()

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (progress < 4) {
                    seekBar?.progress = 4
                }
                numberTextView.text = progress.toString()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                if (seekBar?.progress!! < 4) {
                    seekBar.progress = 4
                }
                numberTextView.text = seekBar.progress.toString()
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                if (seekBar?.progress!! < 4) {
                    seekBar.progress = 4
                }
                numberTextView.text = seekBar.progress.toString()
            }
        })

//        looping the dot 5 times
        val strengthIndicatorContainer = findViewById<LinearLayout>(R.id.strength_indicator_container)
        val layoutInflater = LayoutInflater.from(this)


        for (i in 1..5) {
            val strengthIndicatorView = layoutInflater.inflate(R.layout.dot, null)
            strengthIndicatorContainer.addView(strengthIndicatorView)
        }


//        navigating to the check password strength
        val navigateBtn: Button = findViewById(R.id.myButton)

        val generatePasswordBtn: Button = findViewById(R.id.myGenerateButton)

        navigateBtn.setOnClickListener {
            val intent = Intent(this,MainActivity2::class.java)
            startActivity(intent)
        }

        val showPasswordCardView: CardView = findViewById(R.id.showPassword)
        val textViewForPassword: TextView = showPasswordCardView.findViewById(R.id.generatedPassword)
        val copier: ImageButton = showPasswordCardView.findViewById(R.id.copyPassword)
        val numbers: CheckBox = findViewById(R.id.cbIncludeNumbers)
        val upperCase: CheckBox = findViewById(R.id.cbIncludeUpperCase)
        val symbols: CheckBox = findViewById(R.id.cbIncludeSymbols)

        copier.setOnClickListener{
            val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clipboardData = ClipData.newPlainText("",textViewForPassword.text)
            clipboard.setPrimaryClip(clipboardData)
            Toast.makeText(this,"copied",Toast.LENGTH_SHORT).show()
        }
        generatePasswordBtn.setOnClickListener{
            val password = generatePassword(length = numberTextView.text.toString().toInt(),
                useNumbers = numbers.isChecked, useSymbols = symbols.isChecked, useUpperCase = upperCase.isChecked)

            updateStrengthIndicator(evaluatePasswordStrength(password))
            textViewForPassword.text = password
        }
    }

    private fun evaluatePasswordStrength(password: String): Int {
        var strengthPoints = 0
        if (password.length >= 8) strengthPoints += 1
        if (password.length >= 12) strengthPoints += 1
        if (password.any { it.isUpperCase() }) strengthPoints += 1
        if (password.any { it.isDigit() }) strengthPoints += 1
        val specialCharacters = "!@#$%^&*()-_=+[]{}|;:'\",.<>?/`~"
        if (password.any { it in specialCharacters }) strengthPoints += 1
        return strengthPoints.coerceIn(1, 5)
    }

    //Generate Password
    fun generatePassword(length: Int = 12, useUpperCase: Boolean = false, useSymbols: Boolean = false, useNumbers: Boolean = false): String {
        val lowerCaseLetters = "abcdefghijklmnopqrstuvwxyz"
        val upperCaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
        val digits = "0123456789"
        val specialCharacters = "!@#$%^&*()-_=+[]{}|;:'\",.<>?/`~"

        var allCharacters = lowerCaseLetters


        if (useNumbers)
            allCharacters += lowerCaseLetters
        if (useUpperCase)
            allCharacters += upperCaseLetters
        if (useSymbols)
            allCharacters += specialCharacters

        val password = StringBuilder()
        if (useNumbers)
            password.append(lowerCaseLetters.random())
        if (useUpperCase)
            password.append(upperCaseLetters.random())
        if (useSymbols)
            password.append(specialCharacters.random())
        password.append(digits.random())
        for (i in 4 until length) {
            password.append(allCharacters.random())
        }
        return password.toList().shuffled().joinToString("")
    }

    private fun updateStrengthIndicator(strength: Int) {
        val strengthTextView = findViewById<TextView>(R.id.strengthText)
        val strengthIndicatorContainer = findViewById<LinearLayout>(R.id.strength_indicator_container)
        val layoutInflater = LayoutInflater.from(this)
        strengthIndicatorContainer.removeAllViews()

        for (i in 1..5) {
            val strengthIndicatorView = layoutInflater.inflate(R.layout.dot, null)
            val cardView = strengthIndicatorView.findViewById<CardView>(R.id.strength_indicator_card)

            if (i <= strength) {
                cardView.setCardBackgroundColor(ContextCompat.getColor(this, R.color.dot_green)) // Highlight color
            } else {
                cardView.setCardBackgroundColor(ContextCompat.getColor(this, R.color.lightBlack)) // Default color
            }

            strengthIndicatorContainer.addView(strengthIndicatorView)

            when (strength) {
                1 -> strengthTextView.text = "Too Weak"
                2 -> strengthTextView.text = "Weak"
                3 -> strengthTextView.text = "Normal"
                4 -> strengthTextView.text = "Strong"
                5 -> strengthTextView.text = "Very Strong"
                else -> strengthTextView.text = "Weak"
            }
        }
    }

}
