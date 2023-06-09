package com.ramenmapapp

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore



class StoreActivity : AppCompatActivity() {

    companion object {
        val EXTRA_INDEX = "EXTRA_INDEX"
    }

    private var MRTRoute: String = ""
    private var MRTStation: String = ""
    private var Tel: String = ""
    private var BusinessHour: String = ""
    private var googleMap: String = ""
    private var Address: String = ""
    private var storeName: String = ""

    lateinit var research_btn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_store)

        FirebaseApp.initializeApp(this)

        val position1 = intent.getIntExtra(SelectActivity.EXTRA_POS1, 0)
        val position2 = intent.getIntExtra(SelectActivity.EXTRA_POS2, 0)
        Log.d("in store", "${position1} + ${position2}\n")

        val routeText = findViewById<TextView>(R.id.routeText)
        val stationText = findViewById<TextView>(R.id.stationText)
        //val logTextView = findViewById<TextView>(R.id.logTextView)
        research_btn = findViewById(R.id.research_button)
        research_btn.setOnClickListener { reselect() }

        MRTRoute = getRouteText(position1)
        MRTStation = getStationText(position1, position2)

        routeText.text = MRTRoute
        stationText.text = MRTStation + "站"

        val db = FirebaseFirestore.getInstance()
        val storeList = ArrayList<DocumentSnapshot>()

        db.collection("store") // 替換為你的Firestore集合名稱
            .whereEqualTo("MRTRoute", MRTRoute)
            .whereEqualTo("MRTStation", MRTStation)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    storeName = document.getString("StoreName").toString()
                    val mrtRoute = document.getString("MRTRoute")
                    val logMessage = "StoreName: ${storeName}, MRTRoute: ${mrtRoute}"

                    Tel = document.getString("Tel").toString()
                    BusinessHour = document.getString("BusinessHour").toString()
                    googleMap = document.getString("googleMap").toString()
                    Address = document.getString("Address").toString()

                    // generate list
                    if (storeName != null) {
                        storeList.add(document)
                    }

                    Log.d(TAG, logMessage)
                    //logTextView.append(logMessage + "\n")
                }
                //Log.d(TAG, "RIGHT HERE! + ${storeList[0]} + ${storeList.get(1)}")
                val listView: ListView = findViewById(R.id.store_list)
                val adapter = ArrayAdapter<String>(this, R.layout.each_store_list)
                adapter.addAll(storeList.mapNotNull { it.getString("StoreName") })
                listView.adapter = adapter
                listView.setOnItemClickListener{
                        parent, view, index, id ->
                    val selectedStore = storeList[index]
                    showMap(selectedStore)

                }
            }
            .addOnFailureListener { e ->
                val errorMessage = "Error getting documents: ${e.message}"
                Log.w(TAG, errorMessage)
                //logTextView.append(errorMessage + "\n")
            }
    }

    //捷運線
    private fun getRouteText(position: Int): String {
        return when (position) {
            0 -> "淡水信義線(紅線)"
            1 -> "板南線(藍線)"
            2 -> "松山新店線(綠線)"
            3 -> "中和新蘆線(橘線)"
            4 -> "文湖線(棕線)"
            5 -> "環狀線(黃線)"
            else -> ""
        }
    }

    //捷運站
    private fun getStationText(position1: Int, position2: Int): String {
        if (position1 == 0) {
            return when (position2) {
                0 -> "士林"
                1 -> "大安"
                2 -> "中山"
                3 -> "北投"
                4 -> "台北101/世貿"
                5 -> "台北車站"
                6 -> "民權西路"
                7 -> "石牌"
                8 -> "芝山"
                9 -> "信義安和"
                10 -> "淡水"
                11 -> "象山"
                12 -> "劍潭"
                13 -> "雙連(馬偕紀念醫院)"
                else -> ""
            }
        }

        else if (position1 == 1) {
            return when (position2) {
                0 -> "市政府"
                1 -> "永春"
                2 -> "忠孝復興"
                3 -> "忠孝敦化"
                4 -> "板橋"
                5 -> "國父紀念館"
                6 -> "善導寺（華山）"
                7 -> "龍山寺（艋舺商圈）"
                else -> ""
            }
        }

        else if (position1 == 2) {
            return when (position2) {
                0 -> "新店 (碧潭)"
                1 -> "大坪林"
                2 -> "公館 (台灣大學)"
                3 -> "台電大樓"
                4 -> "古亭"
                5 -> "小南門"
                6 -> "西門"
                7 -> "松江南京"
                8 -> "南京復興"
                9 -> "台北小巨蛋"
                else -> ""
            }
        }

        else if (position1 == 3) {
            return when (position2) {
                0 -> "南勢角"
                1 -> "景安"
                2 -> "頂溪"
                3 -> "中山國小（晴光商圈）"
                4 -> "大橋頭（大橋國小）"
                5 -> "徐匯中學"
                6 -> "三民高中（空中大學）"
                else -> ""
            }
        }

        else if (position1 == 4) {
            return when(position2) {
                0 -> "六張犁"
                1 -> "科技大樓"
                2 -> "大直(實踐大學)"
                3 -> "劍南路(美麗華)"
                4 -> "西湖(德明科大)"
                5 -> "港墘"
                6 ->"東湖"
                else -> ""
            }
        }

        else if (position1 == 5) {
            return when(position2) {
                0 -> "新埔民生"
                1 -> "幸福"
                else -> ""
            }
        }
        return ""
    }

    private fun showMap(selectedStore: DocumentSnapshot) {
        val intent = Intent()
        intent.setClass(this, MapActivity::class.java)
        intent.putExtra("Tel", selectedStore.getString("Tel"))
        intent.putExtra("googleMap", selectedStore.getString("googleMap"))
        intent.putExtra("BusinessHour", selectedStore.getString("BusinessHour"))
        intent.putExtra("StoreName", selectedStore.getString("StoreName"))
        intent.putExtra("Address", selectedStore.getString("Address"))
        intent.putExtra("Station", MRTRoute + " " + MRTStation + "站")
        startActivity(intent)
    }

    private fun reselect() {
        val intent = Intent()
        intent.setClass(this, SelectActivity::class.java)
        startActivity(intent)
    }
}