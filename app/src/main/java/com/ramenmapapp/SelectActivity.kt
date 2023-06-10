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

    private var position1 = -1
    private var position2 = -1
    private var station = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select)

        val route = arrayOf("淡水信義線(紅線)", "板南線(藍線)", "松山新店線(綠線)", "中和新蘆線(橘線)", "文湖線(棕線)", "環狀線(黃線)")

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

                when (position) {
                    0 -> station = mutableListOf("士林", "大安", "中山", "北投", "台北101/世貿", "台北車站", "民權西路", "石牌", "芝山", "信義安和", "淡水", "象山", "劍潭", "雙連(馬偕紀念醫院)")
                    1 -> station = mutableListOf("市政府", "永春", "忠孝復興", "忠孝敦化", "板橋", "國父紀念館", "善導寺（華山）", "龍山寺（艋舺商圈）")
                    2 -> station = mutableListOf("新店 (碧潭)", "大坪林", "公館 (台灣大學)", "台電大樓", "古亭", "小南門", "西門", "松江南京", "南京復興", "台北小巨蛋")
                    3 -> station = mutableListOf("南勢角", "景安", "頂溪", "中山國小（晴光商圈）", "大橋頭（大橋國小）", "徐匯中學", "三民高中（空中大學）")
                    4 -> station = mutableListOf("六張犁", "科技大樓", "大直(實踐大學)", "劍南路(美麗華)", "西湖(德明科大)", "港墘", "東湖")
                    5 -> station = mutableListOf("新埔民生", "幸福")
                }

                stationAdapter.clear()
                stationAdapter.addAll(station)
                stationAdapter.notifyDataSetChanged()
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
        Log.d("in selection", "${position1} + ${position2}\n")
        val intent = Intent()
        intent.setClass(this, StoreActivity::class.java)
        intent.putExtra(EXTRA_POS1, position1)
        intent.putExtra(EXTRA_POS2, position2)
        startActivity(intent)
    }


}