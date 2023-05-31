package com.familygames.trivialwedding
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.familygames.trivialwedding.ahorcado.AhorcadoActivity
import com.familygames.trivialwedding.simon.SimonGameActivity


class MainActivity : AppCompatActivity() {
    private lateinit var codeEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val inputCode: EditText = findViewById(R.id.codeEditText)
        val btnSend: Button = findViewById(R.id.submitBtn)

        btnSend.setOnClickListener {
            val code = inputCode.text.toString()

            if (code == "123456") {
                // Código correcto, pasar a otra pantalla
                val intent = Intent(this, MainQuestions::class.java)
                startActivity(intent)

                //val intent = Intent(this, AhorcadoActivity::class.java)
                //startActivity(intent)

            } else if (code == "987654"){
                // User answered all questions correctly
                val intent = Intent(this, MainFinalScreen::class.java)
                startActivity(intent)
            }else{
                // Código incorrecto, dar aviso
                showAlertDialog()
            }
        }
    }

    private fun showAlertDialog() {
        // Crear un AlertDialog
        val alertDialog = AlertDialog.Builder(this)

        // Establecer el título y el mensaje del diálogo
        alertDialog.setTitle("MEEEEC")
        alertDialog.setMessage("Contraseña errónea. Adioooooos!")

        // Establecer el botón de aceptar y su acción
        alertDialog.setPositiveButton("Aceptar") { dialogInterface: DialogInterface, _: Int ->
            // Acción a realizar al hacer clic en el botón de aceptar
            dialogInterface.dismiss() // Cerrar el diálogo
            finishAffinity();
        }

        // Mostrar el diálogo
        alertDialog.show()
    }
}
