package ru.nowandroid.youtube.rostselmash.charts

import android.graphics.ColorMatrix
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.github.mikephil.charting.animation.Easing
import ru.nowandroid.youtube.rostselmash.R
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import kotlinx.android.synthetic.main.activity_pie_chart.*

class PieChartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pie_chart)

        setPieChart()
    }

    private fun setPieChart() {

        val listPie = ArrayList<PieEntry>()
        val listColors = ArrayList<Int>()
        listPie.add(PieEntry(70F, "RSM-410"))
        listColors.add(resources.getColor(R.color.colorGreen))
        listPie.add(PieEntry(50F, "RSM-380"))
        listColors.add(resources.getColor(R.color.colorOrange))
        listPie.add(PieEntry(30F, "NT-200"))
        listColors.add(resources.getColor(R.color.colorRed))

        val pieDataSet = PieDataSet(listPie, "Комбайны")
        pieChart.centerText = "Комбайны"
        pieDataSet.colors = listColors
        pieDataSet.valueTextSize = 16f

        val pieData = PieData(pieDataSet)
        pieChart.data = pieData

        pieChart.setUsePercentValues(true)
        pieChart.isDrawHoleEnabled = false
        pieChart.description.isEnabled = false
        pieChart.setEntryLabelColor(R.color.colorAccent)
        pieChart.animateY(1400, Easing.EaseInOutQuad)
    }
}
