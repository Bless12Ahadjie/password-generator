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


//        copping the text from the password view
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

            textViewForPassword.text = password
        }

    }

    //Generate Password
    fun generatePassword(length: Int = 12, useUpperCase: Boolean = false, useSymbols: Boolean = false, useNumbers: Boolean = false): String {
        val lowerCaseLetters = "abcdefghijklmnopqrstuvwxyz"
        val upperCaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
        val digits = "0123456789"
        val specialCharacters = "!@#$%^&*()-_=+[]{}|;:'\",.<>?/`~"

        // Initialize the character set with only digits
        var allCharacters = lowerCaseLetters

        // Add lowercase letters if required
        if (useNumbers)
            allCharacters += lowerCaseLetters

        // Add uppercase letters if required
        if (useUpperCase)
            allCharacters += upperCaseLetters

        // Add symbols if required
        if (useSymbols)
            allCharacters += specialCharacters

        val password = StringBuilder()

        // Ensure the password has at least one of each required character type
        if (useNumbers)
            password.append(lowerCaseLetters.random())
        if (useUpperCase)
            password.append(upperCaseLetters.random())
        if (useSymbols)
            password.append(specialCharacters.random())
        password.append(digits.random())

        // Fill the rest of the password length with random characters from all character sets
        for (i in 4 until length) {
            password.append(allCharacters.random())
        }

        // Shuffle the password to ensure the first four characters are not always in the same order
        return password.toList().shuffled().joinToString("")
    }











}
