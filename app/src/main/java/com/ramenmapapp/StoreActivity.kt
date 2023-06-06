package com.ramenmapapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class StoreActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_store)

        val position1 = intent.getIntExtra(SelectActivity.EXTRA_POS1, 0)
        val position2 = intent.getIntExtra(SelectActivity.EXTRA_POS2, 0)
        Log.d("in store", "${position1} + ${position2}\n")
    }
}