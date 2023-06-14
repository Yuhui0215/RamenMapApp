package com.ramenmapapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.webkit.WebView
import android.webkit.WebSettings
import android.webkit.WebViewClient



class MapActivity : AppCompatActivity(){

    lateinit var re_select_btn: Button
    lateinit var store_info: TextView
    lateinit var mapWebView: WebView

    private var googleMap: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
        store_info = findViewById(R.id.store_info)

        //嵌入式地圖
        mapWebView = findViewById(R.id.mapWebView)
        mapWebView.webViewClient = WebViewClient()
        val webSettings: WebSettings = mapWebView.settings
        webSettings.javaScriptEnabled = true

        googleMap = intent.getStringExtra("googleMap").toString()
        val iframeUrl = googleMap
        Log.d("iframeUrl", iframeUrl)
        val iframeHtml = """
                            <!DOCTYPE html>
                            <html>
                            <head>
                            </head>
                            <body style="margin:0;">
                            $iframeUrl
                            </body>
                            </html>
                        """.trimIndent()
        mapWebView.loadData(iframeHtml, "text/html", "UTF-8")

        //其他商店資訊
        val Tel = intent.getStringExtra("Tel")
        val BusinessHour = intent.getStringExtra("BusinessHour")
        val StoreName = intent.getStringExtra("StoreName")
        val Address = intent.getStringExtra("Address")
        val Station = intent.getStringExtra("Station")
        val TelText = if (Tel.isNullOrEmpty()) "沒有電話資訊" else Tel
        val BusinessHourText = if (Tel.isNullOrEmpty()) "沒有營業時間資訊" else BusinessHour
        store_info.text = "店名：$StoreName\n營業時間：$BusinessHourText\n電話：$TelText\n地址：$Address\n鄰近捷運站：$Station"

        //重新選擇店家
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