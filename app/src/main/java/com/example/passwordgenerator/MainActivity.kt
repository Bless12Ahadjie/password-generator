package com.example.passwordgenerator

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
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

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                numberTextView.text = progress.toString()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
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

        navigateBtn.setOnClickListener {
            val intent = Intent(this,MainActivity2::class.java)
            startActivity(intent)


        }

//        copping the text from the password view
        val showPasswordCardView: CardView = findViewById(R.id.showPassword)
        val textViewForPassword: TextView = showPasswordCardView.findViewById(R.id.generatedPassword)
        val copier: ImageButton = showPasswordCardView.findViewById(R.id.copyPassword)

        copier.setOnClickListener{
            val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clipboardData = ClipData.newPlainText("",textViewForPassword.text)
            clipboard.setPrimaryClip(clipboardData)
            Toast.makeText(this,"copied",Toast.LENGTH_SHORT).show()
        }


    }
}