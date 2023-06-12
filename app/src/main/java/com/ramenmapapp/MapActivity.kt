package com.ramenmapapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button

class MapActivity : AppCompatActivity() {

    lateinit var re_select_btn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        re_select_btn = findViewById(R.id.reselect_button)
        re_select_btn.setOnClickListener { back_store() }

        val index = intent.getIntExtra(StoreActivity.EXTRA_INDEX, 0)
        Log.d("!!!!!!CHECK INDEX: ", "${index} \n")
    }

    private fun back_store() {
        val intent = Intent()
        intent.setClass(this, StoreActivity::class.java)
        startActivity(intent)
    }
}