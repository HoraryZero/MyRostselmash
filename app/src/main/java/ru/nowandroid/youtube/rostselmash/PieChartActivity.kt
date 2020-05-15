package ru.nowandroid.youtube.rostselmash

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.github.mikephil.charting.data.PieData

class PieChartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pie_chart)

        setPieChart()
    }

    private fun setPieChart() {

//        val entries = ArrayList<PieEntry>()
//        entries.add((200, ""))
    }
}
