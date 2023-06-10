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
import com.google.firebase.firestore.FirebaseFirestore


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

        val route = arrayOf("淡水信義線(紅線)", "板南線(藍線)", "松山新店線(綠線)", "中和新蘆線(橘線)", "文湖線(棕線)", "環狀線(黃線)")
        val station = resources.getStringArray(R.array.lineList)

        val routeSpinner = findViewById<Spinner>(R.id.route_spinner)
        val stationSpinner = findViewById<Spinner>(R.id.station_spinner)
        val searchBtn = findViewById<Button>(R.id.search_button)

        val routeAdapter = ArrayAdapter(this, R.layout.selected_spinner_style, route)
        routeAdapter.setDropDownViewResource(R.layout.drop_down_spinner_style)
        routeSpinner.adapter = routeAdapter

        val stationAdapter = ArrayAdapter(this, R.layout.selected_spinner_style, station)
        stationAdapter.setDropDownViewResource(R.layout.drop_down_spinner_style)
        stationSpinner.adapter = stationAdapter

        routeSpinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                position1 = position
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // write code to perform some action
            }
        }

        stationSpinner.onItemSelectedListener = object :
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
        val selectedRoute = resources.getStringArray(R.array.lineList)[position1]

        val firestore = FirebaseFirestore.getInstance()
        val collectionRef = firestore.collection("store")

        val query = collectionRef.whereEqualTo("MRTRoute", selectedRoute)

        query.get().addOnSuccessListener { querySnapshot ->
            val matchingDocuments = querySnapshot.documents

            for (document in matchingDocuments) {
                val mrtStation = document.getString("MRTStation")
                Log.d("Matching Document", "MRT Station: $mrtStation")
            }

            // 在此處處理檢索到的文檔，例如顯示在 UI 上或執行其他操作
            // ...

        }.addOnFailureListener { exception ->
            Log.e("Firestore Query", "Error querying Firestore: $exception")
        }
    }

//    private fun search_store(position1: Int, position2: Int) {
//        Log.d("in selection", "${position1} + ${position2}\n")
//        val intent = Intent()
//        intent.setClass(this, StoreActivity::class.java)
//        intent.putExtra(EXTRA_POS1, position1)
//        intent.putExtra(EXTRA_POS2, position2)
//        startActivity(intent)
//    }


}