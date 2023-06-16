package com.ramenmapapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.media.MediaPlayer

class MainActivity : AppCompatActivity() {

    lateinit var start_btn: Button
    private lateinit var mediaPlayer: MediaPlayer


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        start_btn = findViewById(R.id.cover_button)
        start_btn.setOnClickListener { start() }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }


    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
    }

    private fun start() {
        if (!::mediaPlayer.isInitialized) {
            mediaPlayer = MediaPlayer.create(this, R.raw.music)
            mediaPlayer.isLooping = true
        }
        mediaPlayer.start()

        val intent = Intent()
        intent.setClass(this, SelectActivity::class.java)
        startActivity(intent)
    }

}