package ru.nowandroid.youtube.rostselmash.charts

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.github.mikephil.charting.components.XAxis
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
        val bargroup = ArrayList<BarEntry>()
        bargroup.add(BarEntry(1f, 2600f, "1"))
        bargroup.add(BarEntry(2f, 2800f, "2"))
        bargroup.add(BarEntry(3f, 2500f, "3"))
        bargroup.add(BarEntry(4f, 2820f, "4"))
        bargroup.add(BarEntry(5f, 2880f, "5"))
        bargroup.add(BarEntry(6f, 2550f, "6"))
        bargroup.add(BarEntry(7f, 2570f, "7"))
        bargroup.add(BarEntry(8f, 2830f, "8"))
        bargroup.add(BarEntry(9f, 2950f, "9"))
        bargroup.add(BarEntry(10f, 2800f, "10"))
        bargroup.add(BarEntry(11f, 2600f, "11"))
        bargroup.add(BarEntry(12f, 2500f, "12"))

        // creating dataset for Bar Group
        val barDataSet = BarDataSet(bargroup, "Комбайны")

        barDataSet.color = ContextCompat.getColor(this, R.color.colorOrange)

        val data = BarData(barDataSet)
        barChart.setData(data)
        barChart.xAxis.position = XAxis.XAxisPosition.BOTTOM
        barChart.xAxis.labelCount = 11
        barChart.xAxis.enableGridDashedLine(5f, 5f, 0f)
        barChart.axisRight.enableGridDashedLine(5f, 5f, 0f)
        barChart.axisLeft.enableGridDashedLine(5f, 5f, 0f)
        barChart.description.isEnabled = false
        barChart.animateY(100)
        barChart.legend.isEnabled = false
        barChart.setPinchZoom(true)
        barChart.data.setDrawValues(false)
    }
}
