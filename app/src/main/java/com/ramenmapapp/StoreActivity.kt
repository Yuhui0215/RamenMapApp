package com.ramenmapapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore

class StoreActivity : AppCompatActivity() {

    private var MRTRoute: String = ""
    private var MRTStation: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_store)

        val position1 = intent.getIntExtra(SelectActivity.EXTRA_POS1, 0)
        val position2 = intent.getIntExtra(SelectActivity.EXTRA_POS2, 0)
        Log.d("in store", "${position1} + ${position2}\n")

        val routeText = findViewById<TextView>(R.id.routeText)
        val stationText = findViewById<TextView>(R.id.stationText)

        MRTRoute = getRouteText(position1)
        MRTStation = getStationText(position1, position2)

        routeText.text = MRTRoute
        stationText.text = MRTStation
    }

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


}