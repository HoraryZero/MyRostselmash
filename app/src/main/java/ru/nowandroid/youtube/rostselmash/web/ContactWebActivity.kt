package ru.nowandroid.youtube.rostselmash.web

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.nowandroid.youtube.rostselmash.R
import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.webkit.*
import kotlinx.android.synthetic.main.activity_contacts_web.swipeRefresh
import kotlinx.android.synthetic.main.activity_contacts_web.webView

class ContactWebActivity : AppCompatActivity() {

    // Private

    private val BASE_URL = "https://rostselmash.com/company/contact/"

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_contacts_web)

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
}
