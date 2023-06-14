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
import com.google.firebase.firestore.FirebaseFirestore

class StoreActivity : AppCompatActivity() {

    companion object {
        val EXTRA_INDEX = "EXTRA_INDEX"
    }

    private var MRTRoute: String = ""
    private var MRTStation: String = ""
    private var latitude: Double = 0.0
    private var longitude: Double = 0.0
    private var Tel: String = ""
    private var BusinessHour: String = ""

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

        val storeList = ArrayList<String>()

        db.collection("store") // 替換為你的Firestore集合名稱
            .whereEqualTo("MRTRoute", MRTRoute)
            .whereEqualTo("MRTStation", MRTStation)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val storeName = document.getString("StoreName")
                    val mrtRoute = document.getString("MRTRoute")
                    val logMessage = "StoreName: ${storeName}, MRTRoute: ${mrtRoute}"

                    Tel = document.getString("Tel").toString()
                    BusinessHour = document.getString("BusinessHour").toString()
                    latitude = document.getDouble("latitude") ?: 0.0
                    longitude = document.getDouble("longitude") ?: 0.0
                    // generate list
                    if (storeName != null) {
                        storeList.add(storeName)
                    }

                    Log.d(TAG, logMessage)
                    //logTextView.append(logMessage + "\n")
                }
                //Log.d(TAG, "RIGHT HERE! + ${storeList[0]} + ${storeList.get(1)}")
                val listView: ListView = findViewById(R.id.store_list)
                val adapter = ArrayAdapter(this, R.layout.each_store_list, storeList)

                listView.adapter = adapter
                listView.setOnItemClickListener{
                        parent, view, index, id ->
                    showMap(index, latitude, longitude, Tel, BusinessHour)

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

    private fun showMap(index: Int, latitude: Double, longitude: Double, Tel: String, BusinessHour: String) {
        //Log.d("Latitude", latitude.toString())
        //Log.d("Longitude", longitude.toString())
        val iframeUrl = "https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3611.5605232942594!2d121.77319531111549!3d25.150544677648206!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x345d4f063c44f073%3A0x58da11f75dcc14d!2z5ZyL56uL6Ie654Gj5rW35rSL5aSn5a24!5e0!3m2!1szh-TW!2stw!4v1686711462385!5m2!1szh-TW!2stw"

        val modifiedIframeUrl = iframeUrl.replace("!3d25.150544677648206", "!3d$latitude")
                                    .replace("!2d121.77319531111549", "!2d$longitude")

        val intent = Intent(this, MapActivity::class.java)
        intent.putExtra(EXTRA_INDEX, index)
        intent.putExtra("IFRAME_URL", modifiedIframeUrl)
        intent.putExtra("Tel", Tel)
        intent.putExtra("BusinessHour", BusinessHour)
        Log.d("Tel", Tel)
        Log.d("BusinessHour", BusinessHour)
        startActivity(intent)
    }


    /*private fun showMap(index: Int, latitude: Double, longitude: Double) {
        val intent = Intent(this, MapActivity::class.java)
        intent.putExtra(EXTRA_INDEX, index)
        intent.putExtra("LATITUDE", latitude)
        intent.putExtra("LONGITUDE", longitude)
        startActivity(intent)
    }*/

    /*private fun showMap(index: Int) {
        val intent = Intent()
        intent.setClass(this, MapActivity::class.java)
        intent.putExtra(EXTRA_INDEX, index)
        startActivity(intent)
    }*/

    private fun reselect() {
        val intent = Intent()
        intent.setClass(this, SelectActivity::class.java)
        startActivity(intent)
    }
}