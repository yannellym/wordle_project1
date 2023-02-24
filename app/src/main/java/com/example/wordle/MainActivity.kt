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
    // list of fields to use
    private val fieldIds = listOf(
        R.id.textField_guess1 to R.id.textView_guesscheck1,
        R.id.textField_guess2 to R.id.textView_guesscheck2,
        R.id.textField_guess3 to R.id.textView_guesscheck3
    )
    // variables that wont be changing or change very little
    private var attempt = 1
    private var correctGuesses = 0
    private val toolbox = FourLetterWordList()
    private var chosenWord = toolbox.getRandomFourLetterWord().lowercase()
    private lateinit var inputBox: EditText
    private lateinit var winMessage: TextView
    private lateinit var button: Button
    private lateinit var resetBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        inputBox = findViewById(R.id.textInputEditText)
        winMessage = findViewById(R.id.winWord)
        button = findViewById(R.id.button)
        resetBtn = findViewById(R.id.resetBtn)

        //starts our game
        resetGame()
        // sets our button to listen to a click
        button.setOnClickListener {
            val userInput = inputBox.text
            // if the userInput is less than the length of the chosen word's length,
            // display a toast saying that the input must be 4 letters long
            if (chosenWord.length != userInput.length) {
                Toast.makeText(
                    this,
                    "GUESS MUST BE ${chosenWord.length} LETTERS LONG",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (attempt <= 3) {
                val update = checkGuess(chosenWord, userInput)
                val (field, check) = fieldIds[attempt - 1]
                val fieldView = findViewById<TextView>(field)
                val checkView = findViewById<TextView>(check)
                fieldView.text = userInput
                checkView.text = update
                // increase attempt to +1
                attempt++
                // if the checked guess is "0000" they have guessed the correct word!
                if (update == "OOOO") {
                    // display the chosen word along with two party emojis
                    winMessage.setTextColor(Color.MAGENTA)
                    winMessage.text = "\uD83C\uDF89  ${chosenWord.uppercase()}  \uD83C\uDF89"
                    // call the disableButton func to disable/enable buttons
                    disableButton()
                    // display a toast with "Congratulations"
                    Toast.makeText(this, "Congratulations!", Toast.LENGTH_SHORT).show()
                } else if (attempt > 3) {
                    // if the user has already attempted 3 tries, disable the buttons
                    disableButton()
                    // let the user know the game is over by displaying a toast with "game over"
                    Toast.makeText(this, "GAME OVER", Toast.LENGTH_SHORT).show()
                } else if (update == "OOO") { // added condition to count correct guesses
                    // this will update how many times the user has guessed the correct word
                    correctGuesses++
                    Toast.makeText(this, "CORRECT!", Toast.LENGTH_SHORT).show()
                }
            }
        }

        resetBtn.setOnClickListener {
            resetGame()
        }
    }

    private fun resetGame() {
        attempt = 1
        chosenWord = toolbox.getRandomFourLetterWord().lowercase()
        inputBox.text.clear()
        // resets our fields
        fieldIds.forEach { (fieldId, checkId) ->
            val field = findViewById<TextView>(fieldId)
            val check = findViewById<TextView>(checkId)
            field.text = "❓❓❓❓"
            check.text = "❓❓❓❓"
        }
        // sets our submit button to visible and our reset button to invisible
        button.visibility = View.VISIBLE
        winMessage.text = ""
        resetBtn.visibility = View.INVISIBLE
    }

    private fun checkGuess(chosenWord: String, guess: CharSequence): String {
        // checks each letter in the guess word to see if the user has guessed a letter correctly
        // if they user guessed a letter and its matches the letter in the chosen word's index -> 0
        // if they user guessed a letter and it doesnt match the letter in the chosen word's index and its not in chosen word -> X
        // if they user guessed a letter and it doesnt matche the letter in the chosen word's index but it is in chosen word -> +
        return guess.mapIndexed { index, c ->
            when {
                c == chosenWord[index] -> "O"
                c in chosenWord -> "+"
                else -> "X"
            }
        }.joinToString("")
    }

    private fun disableButton() {
        // function to disable/enable the buttons
        button.visibility = View.INVISIBLE
        resetBtn.visibility = View.VISIBLE
    }
}



//    Toast.makeText(this, "WRONG  no ${userInput[i]}", Toast.LENGTH_SHORT).show()
