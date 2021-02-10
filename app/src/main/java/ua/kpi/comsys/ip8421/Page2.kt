package ua.kpi.comsys.ip8421

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.*
import com.google.android.material.tabs.TabLayout

class Page2 : Fragment() {
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(
                R.layout.page2, container, false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val lineChart: LineChart = view.findViewById(R.id.lineChart)
        val pieChart: PieChart = view.findViewById(R.id.pieChart)
        val tabLayout : TabLayout = view.findViewById(R.id.id_tabGraphChart)

        lineChart.visibility = View.VISIBLE
        pieChart.visibility = View.INVISIBLE

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                if(tab.getPosition() == 0) {
                    lineChart.visibility = View.VISIBLE
                    pieChart.visibility = View.INVISIBLE
                } else {
                    lineChart.visibility = View.INVISIBLE
                    pieChart.visibility = View.VISIBLE
                }
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })


        val entries = ArrayList<Entry>()

        for(i in -30..30){
            val point = (i.toFloat() / 10f)
            entries.add(Entry(point, (point*point*point)))
        }

        val vl = LineDataSet(entries, "x^3")

        vl.setDrawValues(false)
        vl.setDrawFilled(true)
        vl.lineWidth = 3f
        vl.fillColor = R.color.black
        vl.fillAlpha = R.color.black

        lineChart.xAxis.labelRotationAngle = 0f

        lineChart.data = LineData(vl)

        lineChart.xAxis.axisMaximum = 3.5f
        lineChart.xAxis.axisMinimum = -3.5f

        val listPie = ArrayList<PieEntry>()
        val listColors = ArrayList<Int>()
        listPie.add(PieEntry(15F, "Yellow"))
        listColors.add(ContextCompat.getColor(context!!, R.color.yellow))
        listPie.add(PieEntry(25F, "Brown"))
        listColors.add(ContextCompat.getColor(context!!, R.color.brown))
        listPie.add(PieEntry(45F, "Gray"))
        listColors.add(ContextCompat.getColor(context!!, R.color.gray))
        listPie.add(PieEntry(10F, "Red"))
        listColors.add(ContextCompat.getColor(context!!, R.color.red))
        listPie.add(PieEntry(5F, "Magenta"))
        listColors.add(ContextCompat.getColor(context!!, R.color.magenta))

        val pieDataSet = PieDataSet(listPie, "")
        pieDataSet.colors = listColors

        val pieData = PieData(pieDataSet)
        pieChart.data = pieData
    }
}