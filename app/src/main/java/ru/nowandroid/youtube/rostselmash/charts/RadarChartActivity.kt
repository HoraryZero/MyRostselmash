package ru.nowandroid.youtube.rostselmash.charts

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import kotlinx.android.synthetic.main.activity_radar_chart.*
import ru.nowandroid.youtube.rostselmash.R

class RadarChartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_radar_chart)

        val yVals = ArrayList<Entry>()
        yVals.add(Entry(1f, 185f, "1"))
        yVals.add(Entry(2f, 265f, "2"))
        yVals.add(Entry(3f, 225f, "3"))
        yVals.add(Entry(4f, 200f, "4"))
        yVals.add(Entry(5f, 250f, "5"))
        yVals.add(Entry(6f, 265f, "6"))
        yVals.add(Entry(7f, 215f, "7"))
        yVals.add(Entry(8f, 275f, "8"))
        yVals.add(Entry(9f, 265f, "9"))
        yVals.add(Entry(10f, 205f, "10"))
        yVals.add(Entry(11f, 195f, "11"))
        yVals.add(Entry(12f, 238f, "12"))

        val set1: LineDataSet
        set1 = LineDataSet(yVals, "DataSet 1")

        set1.color = Color.BLUE
        set1.setCircleColor(Color.BLUE)
        set1.lineWidth = 1f
        set1.circleRadius = 3f
        set1.setDrawCircleHole(false)
        set1.valueTextSize = 0f
        set1.setDrawFilled(false)

        val dataSets = ArrayList<ILineDataSet>()
        dataSets.add(set1)
        val data = LineData(dataSets)

        // set data
        lineChart.setData(data)
        lineChart.description.isEnabled = false
        lineChart.legend.isEnabled = false
        lineChart.setPinchZoom(true)
        lineChart.xAxis.enableGridDashedLine(5f, 5f, 0f)
        lineChart.axisRight.enableGridDashedLine(5f, 5f, 0f)
        lineChart.axisLeft.enableGridDashedLine(5f, 5f, 0f)
        //lineChart.setDrawGridBackground()
        lineChart.xAxis.labelCount = 11
        lineChart.xAxis.position = XAxis.XAxisPosition.BOTTOM
    }
}
