package com.example.wordle

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
    private val toolbox = FourLetterWordList()
    // turn our word to lowercase so the user doesn't have to write in uppercase
    private var chosenWord = toolbox.getRandomFourLetterWord().lowercase()
    // lateinit modifier: https://kotlinlang.org/docs/properties.html#late-initialized-properties-and-variables
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
            // gets the input from the user
            val userInput = inputBox.text
            // if the userInput is less than the length of the chosen word's length,
            // display a toast saying that the input must be 4 letters long
            if (chosenWord.length != userInput.length) {
                Toast.makeText(
                    this,
                    "GUESS MUST BE ${chosenWord.length} LETTERS LONG",
                    Toast.LENGTH_SHORT
                ).show()
                // if the attempt is less than or equal to 3
            } else if (attempt <= 3) {
                // check how many letters the user got correct
                val update = checkGuess(chosenWord, userInput)
                // get the fields that correspond to our current guess Ex, guess 0,1,or 2
                val (field, check) = fieldIds[attempt - 1]
                // reference our fields
                val fieldView = findViewById<TextView>(field)
                val checkView = findViewById<TextView>(check)
                // update our fields
                fieldView.text = userInput
                checkView.text = update
                // increase attempt to +1
                attempt++
                // if the checked guess is "0000" they have guessed the correct word!
                if (update == "✅✅✅✅") {
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
                }
            }
        }
        // when the user clicks on the reset button, call the resetGame function
        resetBtn.setOnClickListener {
            resetGame()
        }
    }
    // function keeps track of the attempts, the chosen word, the user's input, and the field
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
        // if they user guessed a letter and its matches the letter in the chosen word's index -> ✅
        // if they user guessed a letter and it doesn't match the letter in the chosen word's index and its not in chosen word -> ❕
        // if they user guessed a letter and it doesn't match the letter in the chosen word's index but it is in chosen word -> ❌
        //MapIndexed returns a list containing the results of applying the given transform function to each character and its index in the original char sequence
        return guess.mapIndexed { index, c ->
            when {
                c == chosenWord[index] -> "✅"
                c in chosenWord -> "❕"
                else -> "❌"
            }
        }.joinToString("")
    }
    // disables the submit button by making it invisible and turns the reset button visible
    private fun disableButton() {
        // function to disable/enable the buttons
        button.visibility = View.INVISIBLE
        resetBtn.visibility = View.VISIBLE
    }
}

// features I wish I could have added: adding the Confetti library, and changing the color of each individual letter when it was guessed

//    Toast.makeText(this, "WRONG  no ${userInput[i]}", Toast.LENGTH_SHORT).show()
