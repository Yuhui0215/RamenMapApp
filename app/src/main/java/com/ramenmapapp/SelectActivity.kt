package com.ramenmapapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
import android.widget.ArrayAdapter
import android.widget.Button

class SelectActivity : AppCompatActivity() {

    companion object {
        val EXTRA_POS1 = "EXTRA_POS1"
        val EXTRA_POS2 = "EXTRA_POS2"
    }

    var position1 = -1
    var position2 = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select)

        val areas = resources.getStringArray(R.array.areaList)
        val lines = resources.getStringArray(R.array.lineList)

        val areaSpinner = findViewById<Spinner>(R.id.area_spinner)
        val lineSpinner = findViewById<Spinner>(R.id.line_spinner)
        val searchBtn = findViewById<Button>(R.id.search_button)

        val areaAdapter = ArrayAdapter(this, R.layout.selected_spinner_style, areas)
        areaAdapter.setDropDownViewResource(R.layout.drop_down_spinner_style)
        areaSpinner.adapter = areaAdapter

        val lineAdapter = ArrayAdapter(this, R.layout.selected_spinner_style, lines)
        lineAdapter.setDropDownViewResource(R.layout.drop_down_spinner_style)
        lineSpinner.adapter = lineAdapter

        areaSpinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                position1 = position
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // write code to perform some action
            }
        }

        lineSpinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                position2 = position
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // write code to perform some action
            }
        }

        searchBtn.setOnClickListener { search_store(position1, position2) }

    }

    private fun search_store(position1: Int, position2: Int) {
        Log.d("in selection", "${position1} + ${position2}\n")
        val intent = Intent()
        intent.setClass(this, StoreActivity::class.java)
        intent.putExtra(EXTRA_POS1, position1)
        intent.putExtra(EXTRA_POS2, position2)
        startActivity(intent)
    }


}