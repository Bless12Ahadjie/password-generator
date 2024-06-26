package com.example.passwordgenerator

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main2)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val navigateBtn = findViewById<Button>(R.id.myButton)

        val editText = findViewById<EditText>(R.id.EditText)

        editText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                updateStrengthIndicator(evaluatePasswordStrength(s.toString()))
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                updateStrengthIndicator(evaluatePasswordStrength(s.toString()))
            }
        })


        navigateBtn.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)

        }
        val strengthIndicatorContainer = findViewById<LinearLayout>(R.id.strength_indicator_container)
        val layoutInflater = LayoutInflater.from(this)
        for (i in 1..5) {
            val strengthIndicatorView = layoutInflater.inflate(R.layout.dot, null)
            strengthIndicatorContainer.addView(strengthIndicatorView)
        }
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

}