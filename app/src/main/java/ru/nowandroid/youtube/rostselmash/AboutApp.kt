package ru.nowandroid.youtube.rostselmash

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class AboutApp : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_app)

        title = "О приложении Ростсельмаш"
    }
}
