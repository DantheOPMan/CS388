package com.example.project1wordle

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private var targetWord: String = ""
    private var guessCount = 0
    private var gameWon = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // UI elements
        val inputGuess = findViewById<EditText>(R.id.inputGuess)
        val guessButton = findViewById<Button>(R.id.guessButton)
        val resetButton = findViewById<Button>(R.id.resetButton)
        val correctAnswerText = findViewById<TextView>(R.id.correctAnswerText)

        // Guess TextViews and Result TextViews
        val guess1Text = findViewById<TextView>(R.id.guess1Text)
        val guess1Check = findViewById<TextView>(R.id.guess1Check)
        val guess2Text = findViewById<TextView>(R.id.guess2Text)
        val guess2Check = findViewById<TextView>(R.id.guess2Check)
        val guess3Text = findViewById<TextView>(R.id.guess3Text)
        val guess3Check = findViewById<TextView>(R.id.guess3Check)

        // Initially hide guess TextViews and reset button
        guess1Text.visibility = View.GONE
        guess1Check.visibility = View.GONE
        guess2Text.visibility = View.GONE
        guess2Check.visibility = View.GONE
        guess3Text.visibility = View.GONE
        guess3Check.visibility = View.GONE
        resetButton.visibility = View.GONE
        correctAnswerText.visibility = View.GONE

        // Generate the random target word for the game
        targetWord = FourLetterWordList.getRandomFourLetterWord()
        println("Target word: $targetWord")

        guessButton.setOnClickListener {
            if (gameWon) return@setOnClickListener

            val guess = inputGuess.text.toString().trim()

            if (guess.length != 4) {
                Toast.makeText(this, "Please enter a 4-letter word!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            guessCount++
            val result = checkGuess(guess)

            when (guessCount) {
                1 -> {
                    guess1Text.text = "Guess #1: $guess"
                    guess1Check.text = "Result: $result"
                    guess1Text.visibility = View.VISIBLE
                    guess1Check.visibility = View.VISIBLE
                }
                2 -> {
                    guess2Text.text = "Guess #2: $guess"
                    guess2Check.text = "Result: $result"
                    guess2Text.visibility = View.VISIBLE
                    guess2Check.visibility = View.VISIBLE
                }
                3 -> {
                    guess3Text.text = "Guess #3: $guess"
                    guess3Check.text = "Result: $result"
                    guess3Text.visibility = View.VISIBLE
                    guess3Check.visibility = View.VISIBLE
                }
            }

            if (result == "OOOO") {
                gameWon = true
                Toast.makeText(this, "Congratulations! You guessed the word!", Toast.LENGTH_LONG).show()
                println("Player won! Target word was: $targetWord")
                endGame(correctAnswerText, resetButton, guessButton)
            }

            inputGuess.text.clear()

            // If no more guesses are left, end the game
            if (guessCount == 3 && !gameWon) {
                correctAnswerText.text = "Correct Answer: $targetWord"
                correctAnswerText.visibility = View.VISIBLE
                endGame(correctAnswerText, resetButton, guessButton)
            }
        }

        resetButton.setOnClickListener {
            resetGame(guess1Text, guess1Check, guess2Text, guess2Check, guess3Text, guess3Check, correctAnswerText, guessButton, inputGuess, resetButton)
        }
    }

    // Method to check the guess and return the correctness string
    private fun checkGuess(guess: String): String {
        val result = StringBuilder()

        for (i in guess.indices) {
            // We compare uppercase for correctness, but display the result with original formatting.
            when {
                guess[i].uppercaseChar() == targetWord[i].uppercaseChar() -> result.append("O")
                guess[i].uppercaseChar() in targetWord.uppercase() -> result.append("+")
                else -> result.append("X")
            }
        }

        return result.toString()
    }

    // Method to handle end of game logic
    private fun endGame(correctAnswerText: TextView, resetButton: Button, guessButton: Button) {
        Toast.makeText(this, "Game over!", Toast.LENGTH_SHORT).show()
        guessButton.isEnabled = false
        resetButton.visibility = View.VISIBLE
    }

    // Method to reset the game
    private fun resetGame(
        guess1Text: TextView,
        guess1Check: TextView,
        guess2Text: TextView,
        guess2Check: TextView,
        guess3Text: TextView,
        guess3Check: TextView,
        correctAnswerText: TextView,
        guessButton: Button,
        inputGuess: EditText,
        resetButton: Button
    ) {
        guess1Text.visibility = View.GONE
        guess1Check.visibility = View.GONE
        guess2Text.visibility = View.GONE
        guess2Check.visibility = View.GONE
        guess3Text.visibility = View.GONE
        guess3Check.visibility = View.GONE
        correctAnswerText.visibility = View.GONE
        inputGuess.text.clear()

        guessCount = 0
        gameWon = false
        guessButton.isEnabled = true
        resetButton.visibility = View.GONE

        // Generate a new random word
        targetWord = FourLetterWordList.getRandomFourLetterWord()
        println("New Target word: $targetWord")
    }
}
