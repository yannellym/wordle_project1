package com.example.wordle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val button = findViewById<Button>(R.id.button)
        var inputBox = findViewById<EditText>(R.id.textInputEditText)

        button.setOnClickListener {
            val guessedWord = inputBox.text
            Toast.makeText(this, guessedWord, Toast.LENGTH_SHORT).show()
        }

    }
}