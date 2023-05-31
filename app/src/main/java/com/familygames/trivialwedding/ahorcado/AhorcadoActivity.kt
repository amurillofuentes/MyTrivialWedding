package com.familygames.trivialwedding.ahorcado

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.familygames.trivialwedding.R

class AhorcadoActivity  : AppCompatActivity() {
    private lateinit var wordToGuess: String
    private lateinit var guessedWord: CharArray
    private lateinit var guessedLetters: ArrayList<Char>
    private lateinit var hangmanImages: ArrayList<Int>
    private var remainingAttempts: Int = 6

    private lateinit var tvWord: TextView
    private lateinit var tvAttempts: TextView
    private lateinit var etLetter: EditText
    private lateinit var ivHangman: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ahorcado_activity)

        tvWord = findViewById(R.id.tv_word)
        tvAttempts = findViewById(R.id.tv_attempts)
        etLetter = findViewById(R.id.et_letter)
        ivHangman = findViewById(R.id.iv_hangman)

        initGame()
    }

    private fun initGame() {
        // Palabra a adivinar
        wordToGuess = "hangman"
        guessedWord = CharArray(wordToGuess.length)
        guessedLetters = ArrayList()

        // Inicializar la palabra adivinada con guiones bajos
        for (i in wordToGuess.indices) {
            guessedWord[i] = '_'
        }

        tvWord.text = guessedWord.joinToString(" ")
        tvAttempts.text = getString(R.string.remaining_attempts, remainingAttempts)

        hangmanImages = ArrayList()
        hangmanImages.add(R.drawable.hangman_0)
        hangmanImages.add(R.drawable.hangman_1)
        hangmanImages.add(R.drawable.hangman_2)
        hangmanImages.add(R.drawable.hangman_3)
        hangmanImages.add(R.drawable.hangman_4)
        hangmanImages.add(R.drawable.hangman_5)
        hangmanImages.add(R.drawable.hangman_6)
    }

    fun onGuessLetter(view: View) {
        val letter = etLetter.text.toString().lowercase().singleOrNull()

        if (letter != null && letter.isLetter()) {
            if (guessedLetters.contains(letter)) {
                // La letra ya fue adivinada anteriormente
                etLetter.error = getString(R.string.letter_already_guessed)
            } else {
                guessedLetters.add(letter)

                if (wordToGuess.contains(letter)) {
                    // La letra está en la palabra
                    for (i in wordToGuess.indices) {
                        if (wordToGuess[i] == letter) {
                            guessedWord[i] = letter
                        }
                    }

                    tvWord.text = guessedWord.joinToString(" ")

                    if (!guessedWord.contains('_')) {
                        // Todas las letras han sido adivinadas
                        endGame(true)
                    }
                } else {
                    // La letra no está en la palabra
                    remainingAttempts--

                    if (remainingAttempts == 0) {
                        // Se han agotado los intentos
                        endGame(false)
                    } else {
                        tvAttempts.text = getString(R.string.remaining_attempts, remainingAttempts)
                        ivHangman.setImageResource(hangmanImages[6 - remainingAttempts])
                    }
                }
            }
        } else {
            // El usuario no ingresó una letra válida
            etLetter.error = getString(R.string.invalid_letter)
        }

        etLetter.text.clear()
    }

    private fun endGame(hasWon: Boolean) {
        if (hasWon) {
            tvAttempts.text = getString(R.string.you_win)
        } else {
            tvAttempts.text = getString(R.string.you_lose)
            tvWord.text = wordToGuess
        }

        etLetter.isEnabled = false
    }
}