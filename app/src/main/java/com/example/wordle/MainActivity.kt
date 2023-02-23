package com.example.wordle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val button = findViewById<Button>(R.id.button)
        var inputBox = findViewById<EditText>(R.id.textInputEditText)
        var toolbox = FourLetterWordList()
        val chosenWord: String = toolbox.getRandomFourLetterWord().lowercase()



        button.setOnClickListener {
            Log.d("chosen word", chosenWord)
            val userInput = inputBox.text
            var update = checkGuess(chosenWord, userInput)
            var attempt = 1
            updateField(update, attempt, userInput)

        }
    }
    // * Parameters / Fields:
    //     *   wordToGuess : String - the target word the user is trying to guess
    //     *   guess : String - what the user entered as their guess
    //     *
    //     * Returns a String of 'O', '+', and 'X', where:
    //     *   'O' represents the right letter in the right place
    //     *   '+' represents the right letter in the wrong place
    //     *   'X' represents a letter not in the target word
    //     */
    private fun checkGuess(chosenWord: String, guess: Editable): String {
        var result = ""
        for (i in 0..3) {
            if (guess[i] == chosenWord[i]) {
                result += "O"
            } else if (guess[i] in chosenWord) {
                result += "+"
            } else {
                result += "X"
            }
        }
        Toast.makeText(this, result, Toast.LENGTH_SHORT).show()
        return result
    }

    private fun updateField(update: String, attempt: Int, userInput: String): String {
        if (attempt==1){
            var field = findViewById<TextView>(R.id.textField_guess1)
            field.text = userInput
            var check1 = findViewById<TextView>(R.id.textView_guesscheck1)
            check1.text = update
        } else if (attempt==2){
            var field = findViewById<TextView>(R.id.textField_guess1)
            field.text = userInput
            var check1 = findViewById<TextView>(R.id.textView_guesscheck1)
            check1.text = update

        } else if (attempt==3){
            var field = findViewById<TextView>(R.id.textField_guess1)
            field.text = userInput
            var check1 = findViewById<TextView>(R.id.textView_guesscheck1)
            check1.text = update

        }else {
            Toast.makeText(this, "GAME OVER", Toast.LENGTH_SHORT).show()

        }
        return
    }
}

//    Toast.makeText(this, "WRONG  no ${userInput[i]}", Toast.LENGTH_SHORT).show()
