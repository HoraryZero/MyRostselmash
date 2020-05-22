package ru.nowandroid.youtube.rostselmash.web

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.nowandroid.youtube.rostselmash.R
import android.annotation.SuppressLint
import android.app.Service
import android.graphics.Bitmap
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.webkit.*
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_product_web.*

class ProductWebActivity : AppCompatActivity() {

    private val BASE_URL = "https://rostselmash.com/products/"
    // Проверка соединения
    private var context = this
    private var connectivity: ConnectivityManager? = null
    private var connectInfo: NetworkInfo? = null
    private var toastConnectedInfo = ""
    private var toastDisconnectedInfo = "Отсутствует соединение с интернетом"
    private val duration = Toast.LENGTH_SHORT

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_product_web)

        // Проверка на наличие соеднинения
        if (checkConnect()) {

            Toast.makeText(context, toastConnectedInfo, duration)
        } else {

            Toast.makeText(context, toastDisconnectedInfo, duration).show()
            this.finish()
        }

        // Refresh
        swipeRefresh.setOnRefreshListener {
            webView.reload()
        }

        // WebView
        webView.webChromeClient = object : WebChromeClient() {

        }

        webView.webViewClient = object : WebViewClient() {

            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                return false
            }

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)

                swipeRefresh.isRefreshing = true
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)

                swipeRefresh.isRefreshing = false
            }

        }

        val settings = webView.settings
        settings.javaScriptEnabled = true

        webView.loadUrl(BASE_URL)

    }

    override fun onBackPressed() {
        if(webView.canGoBack()) {
            webView.goBack()
        } else {
            super.onBackPressed()
        }
    }

    private fun checkConnect() : Boolean {

        connectivity = context.getSystemService(Service.CONNECTIVITY_SERVICE) as ConnectivityManager

        if(connectivity != null) {

            connectInfo = connectivity!!.activeNetworkInfo

            if (connectInfo != null) {

                if (connectInfo!!.state == NetworkInfo.State.CONNECTED) {
                    return true
                }
            }
        }
        return false
    }
}
