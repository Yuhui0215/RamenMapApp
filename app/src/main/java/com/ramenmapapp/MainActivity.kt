package com.ramenmapapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ListView

class MainActivity : AppCompatActivity() {

    lateinit var start_btn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        start_btn = findViewById(R.id.cover_button)
        start_btn.setOnClickListener { start() }
    }
    private fun start() {
        val intent = Intent()
        intent.setClass(this, SelectActivity::class.java)
        startActivity(intent)
    }
}