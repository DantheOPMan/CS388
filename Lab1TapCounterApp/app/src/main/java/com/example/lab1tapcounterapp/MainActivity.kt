package com.example.lab1tapcounterapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val button = findViewById<Button>(R.id.countButton)
        val countText = findViewById<TextView>(R.id.countText)
        var counter = 0
        button.setOnClickListener{
            Toast.makeText(this, "Clicked button!", Toast.LENGTH_SHORT).show()
            counter++
            countText.text = counter.toString()
        }
    }
}