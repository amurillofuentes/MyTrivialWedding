package com.familygames.trivialwedding

import android.app.AlertDialog
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.util.TypedValue
import android.text.InputType
import android.text.TextWatcher
import android.view.Gravity
import android.widget.Button
import android.widget.EditText
import android.widget.GridLayout
import android.view.ViewGroup.LayoutParams
import androidx.appcompat.app.AppCompatActivity
import com.familygames.trivialwedding.sudoku.GRID_SIZE
import com.familygames.trivialwedding.sudoku.Level
import com.familygames.trivialwedding.sudoku.Sudoku

class SudokuActivity : AppCompatActivity() {

    private lateinit var sudokuGrid: Array<Array<EditText>>
    private lateinit var checkButton: Button
    private lateinit var s : Sudoku

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sudoku)

        val dificultad = intent.getIntExtra("Dificultad", 0)

        if (dificultad<=2) s=Sudoku.Builder().setLevel(Level.FULL).build()
        if (dificultad in 3..4) s=Sudoku.Builder().setLevel(Level.AMATEUR).build()
        if (dificultad in 5..6) s=Sudoku.Builder().setLevel(Level.MIDDLE).build()
        if (dificultad in 7..8) s=Sudoku.Builder().setLevel(Level.PROFESSIONAL).build()
        if (dificultad>=9) s=Sudoku.Builder().setLevel(Level.EXPERT).build()

        if (s==null) s=Sudoku.Builder().setLevel(Level.MIDDLE).build()
        s.printGrid()
        fillScreen(s)

        checkButton = findViewById(R.id.checkButton)
        checkButton.setOnClickListener { checkSudoku() }
    }

    override fun onBackPressed() {
        // No hacer nada para evitar que se cierre la actividad
    }


    private fun checkSudoku() {
        val solved=s.compareGrids(s.grid,s.grid_backup)

        // Crear un AlertDialog
        val alertDialog = AlertDialog.Builder(this)

        // Establecer el título y el mensaje del diálogo
        if(solved){
            alertDialog.setTitle("¡¡¡¡¡FELICIDADES!!!!")
            alertDialog.setMessage("Has resuelto el sudoku. Puedes seguir jugando :) ")
            // Establecer el botón de aceptar y su acción
            alertDialog.setPositiveButton("Aceptar") { dialogInterface: DialogInterface, _: Int ->
                // Acción a realizar al hacer clic en el botón de aceptar
                dialogInterface.dismiss() // Cerrar el diálogo
                this.finish()
            }
        }else{
            alertDialog.setTitle("¡¡¡¡¡QUE PUTADA!!!!")
            alertDialog.setMessage("JAJAJA!!! Ya sabes lo que va a pasar... xD ")
            // Establecer el botón de aceptar y su acción
            alertDialog.setPositiveButton("Aceptar") { dialogInterface: DialogInterface, _: Int ->
                // Acción a realizar al hacer clic en el botón de aceptar
                dialogInterface.dismiss() // Cerrar el diálogo
                finishAffinity()
            }
        }

        // Mostrar el diálogo
        alertDialog.show()

    }

    private fun onEditTextValueChanged(editText: EditText, newvalue: CharSequence?) {

        val tagValue = editText.tag as String
        val parts = tagValue.split("_")
        val row = parts[0].toInt()
        val column = parts[1].toInt()

        s.grid[row][column]=newvalue.toString().toInt()
        // Realiza las acciones necesarias con la fila y columna obtenidas del tag
    }

    private fun fillScreen(s: Sudoku) {
        val gridLayout: GridLayout = findViewById(R.id.gridLayout)

        val screenWidth = resources.displayMetrics.widthPixels
        val columnCount = 9

        val editTextWidth = screenWidth / columnCount

        var gridSudoku = Array(GRID_SIZE) { IntArray(GRID_SIZE) {0} }
        gridSudoku=s.grid

        val gridSize = gridSudoku.size
        // Recorrer filas y columnas de la matriz
        for (row in 0 until gridSize) {
            for (column in 0 until gridSize) {
                val cellValue = gridSudoku[row][column]

                // Crear una instancia de EditText
                val editText = EditText(this)
                // Configurar los atributos del EditText
                editText.layoutParams = LayoutParams(editTextWidth, LayoutParams.WRAP_CONTENT)
                editText.gravity = Gravity.CENTER
                editText.inputType = InputType.TYPE_CLASS_NUMBER
                editText.background.setColorFilter(Color.BLACK, android.graphics.PorterDuff.Mode.SRC_IN)
                editText.setTypeface(null, Typeface.BOLD)
                editText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20f)
                editText.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(1))
                editText.tag = row.toString()+"_"+column.toString()

                if (cellValue==0){
                    editText.setText("0")
                    editText.setBackgroundColor(0xFFFFFF)
                    editText.setTextColor(Color.parseColor("#FF0000"))
                    editText.setTypeface(null, Typeface.BOLD)
                    editText.addTextChangedListener(object : TextWatcher {
                        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                            // No necesitas implementar nada aquí, pero la interfaz requiere que lo hagas.
                        }

                        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                            if (!s.isNullOrEmpty()) {
                                onEditTextValueChanged(editText, s)
                            }
                        }

                        override fun afterTextChanged(s: Editable?) {
                            // No necesitas implementar nada aquí, pero la interfaz requiere que lo hagas.
                        }
                    })
                }else{
                    editText.setText(cellValue.toString())
                    editText.setBackgroundColor(Color.parseColor("#D3D3D3"))
                    editText.setTextColor(Color.parseColor("#000000"))
                    editText.isEnabled = false
                }

                // Agregar el EditText a un contenedor existente
                gridLayout.addView(editText)
            }
        }





    }



}
