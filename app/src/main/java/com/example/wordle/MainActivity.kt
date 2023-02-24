package com.example.wordle

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button: Button = findViewById<Button>(R.id.button)
        var inputBox: EditText = findViewById<EditText>(R.id.textInputEditText)
        var toolbox: FourLetterWordList = FourLetterWordList()
        val chosenWord: String = toolbox.getRandomFourLetterWord().lowercase()
        val winMessage: TextView = findViewById<TextView>(R.id.winWord)
        var attempt: Int = 1

        if (attempt < 4) {

            button.setOnClickListener {
                Log.d("chosen word", chosenWord)
                val userInput = inputBox.text

                if (chosenWord.length > 3) {
                    var update = checkGuess(chosenWord, userInput)
                    attempt = updateField(update, attempt, userInput, winMessage)
                } else {
                    Toast.makeText(this, "GUESS MUST BE 4 LETTERS LONG", Toast.LENGTH_SHORT).show()
                }
            }
        } else{
            Toast.makeText(this, "GAME OVER", Toast.LENGTH_SHORT).show()
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
        return result
    }

    private fun updateField(update: String, attempt: Int, userInput: Editable, winMessage: TextView): Int {
        if (attempt==1){

            var field = findViewById<TextView>(R.id.textField_guess1)
            field.text = userInput
            var check1 = findViewById<TextView>(R.id.textView_guesscheck1)
            check1.text = update

        } else if (attempt==2){

            var field = findViewById<TextView>(R.id.textField_guess2)
            field.text = userInput
            var check2 = findViewById<TextView>(R.id.textView_guesscheck2)
            check2.text = update

        } else if (attempt==3 || update == "OOOO"){
            var field = findViewById<TextView>(R.id.textField_guess3)
            field.text = userInput
            var check3 = findViewById<TextView>(R.id.textView_guesscheck3)
            check3.text = update
            userWon(winMessage)
        }else {
            Toast.makeText(this, "GAME OVER", Toast.LENGTH_SHORT).show()
        }
        return attempt + 1
    }
    private fun userWon(winMessage: TextView) {
        var submit = findViewById<Button>(R.id.button)
        winMessage.text = "CONGRATULATIONS YOU WON!"
        submit.setBackgroundColor(Color.DKGRAY)
        submit.isEnabled = false
    }
}

//    Toast.makeText(this, "WRONG  no ${userInput[i]}", Toast.LENGTH_SHORT).show()
