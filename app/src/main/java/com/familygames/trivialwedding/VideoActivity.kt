package com.familygames.trivialwedding

import android.app.AlertDialog
import android.content.DialogInterface
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.MediaController
import android.widget.Toast
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity


class VideoActivity : AppCompatActivity() {

    // declaring a null variable for VideoView
    var simpleVideoView: VideoView? = null

    // declaring a null variable for MediaController
    var mediaControls: MediaController? = null

    var video = 0

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.video_activity)

        video = intent.getIntExtra("Video", 1)

        // assigning id of VideoView from
        // activity_main.xml layout file
        simpleVideoView = findViewById<View>(R.id.simpleVideoView) as VideoView

        if (mediaControls == null) {
            // creating an object of media controller class
            mediaControls = MediaController(this)

            // set the anchor view for the video view
            mediaControls!!.setAnchorView(this.simpleVideoView)
        }

        // set the media controller for video view
        simpleVideoView!!.setMediaController(mediaControls)

        // set the absolute path of the video file which is going to be played
        if(video==1){
            simpleVideoView!!.setVideoURI(Uri.parse("android.resource://" + packageName + "/" + R.raw.motivacion1))
        }else if(video==2){
            simpleVideoView!!.setVideoURI(Uri.parse("android.resource://" + packageName + "/" + R.raw.motivacion1))
        } else if(video==3){
            simpleVideoView!!.setVideoURI(Uri.parse("android.resource://" + packageName + "/" + R.raw.motivacion1))
        }else if(video==4){
            simpleVideoView!!.setVideoURI(Uri.parse("android.resource://" + packageName + "/" + R.raw.motivacion1))
        }

        simpleVideoView!!.requestFocus()

        // starting the video
        simpleVideoView!!.start()

        // display a toast message
        // after the video is completed
        simpleVideoView!!.setOnCompletionListener {
            finishVideo()
        }

        // display a toast message if any
        // error occurs while playing the video
        simpleVideoView!!.setOnErrorListener { mp, what, extra ->
            Toast.makeText(applicationContext, "An Error Occurred " +
                    "While Playing Video !!!", Toast.LENGTH_LONG).show()
            false
        }
    }

    private fun finishVideo() {
        // Crear un AlertDialog
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle("¡¡¡¡¡VAMOOOOS!!!!")
        alertDialog.setMessage("Sigue avanzando, que vas muy bien!!! :) ")
        // Establecer el botón de aceptar y su acción
        alertDialog.setPositiveButton("Aceptar") { dialogInterface: DialogInterface, _: Int ->
            // Acción a realizar al hacer clic en el botón de aceptar
            dialogInterface.dismiss() // Cerrar el diálogo
            this.finish()
        }
        // Mostrar el diálogo
        alertDialog.show()
    }
}
