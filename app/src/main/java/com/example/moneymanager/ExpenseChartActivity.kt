package com.example.moneymanager

import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.moneymanager.widget.Expense
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import java.text.SimpleDateFormat
import java.util.*

class ExpenseChartActivity : AppCompatActivity() {

    private lateinit var barChart: BarChart
    private val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.expense_chart_view)

        val backButton: Button = findViewById(R.id.backButton)
        barChart = findViewById(R.id.barChart)

        backButton.setOnClickListener {
            finish()
        }

        // Отримайте дані витрат з Intent
        val expenses = intent.getParcelableArrayListExtra<Expense>("expenses") ?: listOf()

        setupBarChart(expenses)
    }

    private fun setupBarChart(expenses: List<Expense>) {
        val entries = ArrayList<BarEntry>()
        val labels = ArrayList<String>()

        expenses.forEachIndexed { index, expense ->
            entries.add(BarEntry(index.toFloat(), expense.amount.toFloat()))
            labels.add("${dateFormat.format(expense.date)}\n${expense.amount}")
        }

        val dataSet = BarDataSet(entries, "Витрати")
        dataSet.colors = listOf(Color.RED, Color.BLUE) // Можна змінити кольори
        dataSet.valueTextColor = Color.BLACK
        dataSet.valueTextSize = 10f

        val data = BarData(dataSet)
        barChart.data = data

        val xAxis = barChart.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.valueFormatter = IndexAxisValueFormatter(labels)
        xAxis.granularity = 1f
        xAxis.setDrawGridLines(false)

        val description = Description()
        description.text = "Витрати за дати"
        barChart.description = description

        barChart.invalidate()
    }
}
