package ru.nowandroid.youtube.rostselmash.charts

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import kotlinx.android.synthetic.main.activity_bar_chart.*
import ru.nowandroid.youtube.rostselmash.R


class BarChartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bar_chart)

        setBarChart()
    }

    private fun setBarChart() {
        val entries = ArrayList<BarEntry>()
        entries.add(BarEntry(90f, 0))
        entries.add(BarEntry(87f, 1))
        entries.add(BarEntry(93f, 2))
        entries.add(BarEntry(98f, 3))
        entries.add(BarEntry(97f, 4))
        entries.add(BarEntry(103f, 5))
        entries.add(BarEntry(115f, 6))
        entries.add(BarEntry(98f, 7))
        entries.add(BarEntry(114f, 8))
        entries.add(BarEntry(93f, 9))
        entries.add(BarEntry(90f, 10))

        val barDataSet = BarDataSet(entries, "Количество комбайнов за год")

        val labels = ArrayList<String>()
        labels.add("2010")
        labels.add("2011")
        labels.add("2012")
        labels.add("2013")
        labels.add("2014")
        labels.add("2015")
        labels.add("2016")
        labels.add("2017")
        labels.add("2018")
        labels.add("2019")
        labels.add("2020")

        val data = BarData(labels, barDataSet)
        barChart.data = data //

        barChart.setDescription("")  // Подпись в нижнем правом углу

        //barDataSet.setColors(ColorTemplate.COLORFUL_COLORS)
        barDataSet.color = resources.getColor(R.color.colorAccent)

        barChart.animateY(5000)
    }
}
