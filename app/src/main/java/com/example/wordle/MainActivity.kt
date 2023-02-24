package com.example.wordle

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var field1 = findViewById<TextView>(R.id.textField_guess1)
        var check1 = findViewById<TextView>(R.id.textView_guesscheck1)
        var field2 = findViewById<TextView>(R.id.textField_guess2)
        var check2 = findViewById<TextView>(R.id.textView_guesscheck2)
        var field3 = findViewById<TextView>(R.id.textField_guess3)
        var check3 = findViewById<TextView>(R.id.textView_guesscheck3)
        var reset = findViewById<Button>(R.id.resetBtn)
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
                    attempt = updateField(update, attempt, userInput, field1, field2, field3, check1, check2, check3)
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
            var letter = guess[i]
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

    private fun updateField(update: String, attempt: Int, userInput: Editable,
                            field1:TextView, field2:TextView, field3:TextView,
                            check1:TextView, check2:TextView, check3:TextView): Int {
        if (attempt==1){

            field1.text = userInput
            check1.text = update

        } else if (attempt==2){
            field2.text = userInput
            check2.text = update

        } else if (attempt==3){

            field3.text = userInput
            check3.text = update
            disableButton(field1, field2, field3, check1, check2, check3)
            if (update == "OOOO") {
                Toast.makeText(this, "Congrats", Toast.LENGTH_SHORT).show()
            } else{
                Toast.makeText(this, "GAME OVER", Toast.LENGTH_SHORT).show()
            }
        }
        return attempt + 1
    }
    private fun disableButton(field1:TextView, field2:TextView, field3:TextView,
                              check1:TextView, check2:TextView, check3:TextView) {
        var submit = findViewById<Button>(R.id.button)
        var reset = findViewById<Button>(R.id.resetBtn)
        submit.visibility = View.INVISIBLE
        reset.visibility = View.VISIBLE
        field1.text = "❓❓❓❓"
        field2.text = "❓❓❓❓"
        field3.text = "❓❓❓❓"
        check1.text = "❓❓❓❓"
        check2.text = "❓❓❓❓"
        check3.text = "❓❓❓❓"

    }

}

//    Toast.makeText(this, "WRONG  no ${userInput[i]}", Toast.LENGTH_SHORT).show()
