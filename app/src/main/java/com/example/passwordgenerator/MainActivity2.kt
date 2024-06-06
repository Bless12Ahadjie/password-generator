package com.example.passwordgenerator

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
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




}