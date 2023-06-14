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


class MapActivity : AppCompatActivity() {

    lateinit var re_select_btn: Button
    lateinit var store_info: TextView
    lateinit var mapWebView: WebView

    private var latitude: Double = 0.0
    private var longitude: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        mapWebView = findViewById(R.id.mapWebView)
        mapWebView.webViewClient = WebViewClient()

        store_info = findViewById(R.id.store_info)

        val webSettings: WebSettings = mapWebView.settings
        webSettings.javaScriptEnabled = true

        val iframeUrl = intent.getStringExtra("IFRAME_URL")
        //val iframeUrl = "https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3611.5605232942594!2d121.77319531111549!3d25.150544677648206!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x345d4f063c44f073%3A0x58da11f75dcc14d!2z5ZyL56uL6Ie654Gj5rW35rSL5aSn5a24!5e0!3m2!1szh-TW!2stw!4v1686711462385!5m2!1szh-TW!2stw"

        val iframeHtml = """
                            <!DOCTYPE html>
                            <html>
                            <head>
                            </head>
                            <body style="margin:0;">
                            <iframe src="$iframeUrl" width="100%" height="100%" frameborder="0" style="border:0;" allowfullscreen="" loading="lazy"></iframe>
                            </body>
                            </html>
                        """.trimIndent()

        mapWebView.loadData(iframeHtml, "text/html", "UTF-8")

        latitude = intent.getDoubleExtra("LATITUDE", 0.0)
        longitude = intent.getDoubleExtra("LONGITUDE", 0.0)

        val Tel = intent.getStringExtra("Tel")
        val BusinessHour = intent.getStringExtra("BusinessHour")
        store_info.text = "電話：沒有資訊\n營業時間：$BusinessHour"
        //store_info.text = "電話：$Tel\n營業時間：$BusinessHour"

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