package com.familygames.trivialwedding.simon

import android.app.AlertDialog
import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.view.MotionEvent
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.familygames.trivialwedding.R
import com.familygames.trivialwedding.sudoku.Level
import com.familygames.trivialwedding.sudoku.Sudoku

class SimonGameActivity : AppCompatActivity() {

    private val colors = arrayOf(Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW)
    private val sequence = mutableListOf<Int>()
    private var playerTurn = false
    private var currentIndex = 0

    private lateinit var statusTextView: TextView
    private lateinit var startButton: Button
    private lateinit var colorButtons: List<Button>

    private var sizeInitSequence = 0
    private var speedInitSequence = 1000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.simon_activity)

        val dificultad = intent.getIntExtra("Dificultad", 0)

        if (dificultad<=2){
            sizeInitSequence=1
            speedInitSequence=1000
        }
        if (dificultad in 3..4){
            sizeInitSequence=6
            speedInitSequence=875
        }
        if (dificultad in 5..6){
            sizeInitSequence=11
            speedInitSequence=750
        }
        if (dificultad in 7..8){
            sizeInitSequence=16
            speedInitSequence=625
        }
        if (dificultad>=9){
            sizeInitSequence=21
            speedInitSequence=500
        }

        statusTextView = findViewById(R.id.statusTextView)
        startButton = findViewById(R.id.startButton)
        colorButtons = listOf(
            findViewById(R.id.redButton),
            findViewById(R.id.greenButton),
            findViewById(R.id.blueButton),
            findViewById(R.id.yellowButton)
        )

        setButtonEnabled(true)

        startButton.setOnClickListener {
            startGame()
        }

        colorButtons.forEachIndexed { index, button ->

            button.setOnTouchListener { view, event ->
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        if (playerTurn) {
                            view.alpha = 0.5f
                        }
                        true
                    }
                    MotionEvent.ACTION_UP -> {
                        view.alpha = 1.0f
                        if (playerTurn) {
                            handlePlayerInput(index)
                        }
                        true
                    }
                    else -> false
                }
            }


        }

        statusTextView.text = getString(R.string.watch_sequence)
    }

    private fun startGame() {
        sequence.clear()
        playerTurn = false
        currentIndex = 0


        for (i in 1..sizeInitSequence) {
            generateNextSequence()
        }
        showSequence()
    }

    private fun generateNextSequence() {
        val nextColor = (0..3).random()
        sequence.add(nextColor)
    }

    private fun showSequence() {
        Handler().postDelayed({
            for (i in 0 until sequence.size) {
                val colorIndex = sequence[i]
                val button = colorButtons[colorIndex]

                Handler().postDelayed({
                    button.alpha = 0.5f
                    Handler().postDelayed({
                        button.alpha = 1.0f

                        if (i == sequence.size - 1) {
                            playerTurn = true
                            statusTextView.text = getString(R.string.your_turn)
                        }
                    }, 500)
                }, (i + 1) * 1000L)
            }
        }, speedInitSequence.toLong())
    }

    private fun handlePlayerInput(colorIndex: Int) {
        val alertDialog = AlertDialog.Builder(this)
        if (colorIndex == sequence[currentIndex]) {
            currentIndex++
            if (currentIndex==5){
                //Resueltas 5 iteraciones. Perfect.
                alertDialog.setTitle("¡¡¡¡¡FELICIDADES!!!!")
                alertDialog.setMessage("Has resuelto la combinación. Puedes seguir jugando :) ")
                // Establecer el botón de aceptar y su acción
                alertDialog.setPositiveButton("Aceptar") { dialogInterface: DialogInterface, _: Int ->
                    // Acción a realizar al hacer clic en el botón de aceptar
                    dialogInterface.dismiss() // Cerrar el diálogo
                    this.finish()
                }
                alertDialog.show()
            }else{
                //sigue jugando
                if (currentIndex == sequence.size) {
                    playerTurn = false
                    currentIndex = 0
                    statusTextView.text = getString(R.string.correct_sequence)
                    generateNextSequence()
                    showSequence()
                }
            }

        } else {
            alertDialog.setTitle("¡¡¡¡¡QUE PUTADA!!!!")
            alertDialog.setMessage("JAJAJA!!! Ya sabes lo que va a pasar... xD ")
            // Establecer el botón de aceptar y su acción
            alertDialog.setPositiveButton("Aceptar") { dialogInterface: DialogInterface, _: Int ->
                // Acción a realizar al hacer clic en el botón de aceptar
                dialogInterface.dismiss() // Cerrar el diálogo
                finishAffinity()
            }
            alertDialog.show()
        }
    }



    private fun setButtonEnabled(enabled: Boolean) {
        startButton.isEnabled = enabled
        colorButtons.forEach { button ->
            button.isEnabled = enabled
        }
    }
}
