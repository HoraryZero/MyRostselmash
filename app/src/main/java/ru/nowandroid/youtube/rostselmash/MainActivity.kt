package ru.nowandroid.youtube.rostselmash

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.analytics.FirebaseAnalytics


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        // SplashScreen
        Thread.sleep(2000)
        setTheme(R.style.AppTheme)

        //Analytics Event
        val analytics = FirebaseAnalytics.getInstance(this)
        val bundle = Bundle()
        bundle.putString("message", "Open app")
        analytics.logEvent("InitScreen", bundle)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}








