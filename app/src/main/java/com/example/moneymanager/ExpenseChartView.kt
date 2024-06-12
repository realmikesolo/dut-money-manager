package com.example.moneymanager

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.example.moneymanager.widget.Expense
import java.text.SimpleDateFormat
import java.util.*

class ExpenseChartView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private val paint = Paint().apply {
        color = Color.BLUE
        strokeWidth = 8f
    }

    private val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    private var expenseData = listOf<Pair<String, Pair<Int, Double>>>()

    fun setExpenses(expenses: List<Expense>) {
        expenseData = expenses.groupBy { dateFormat.format(it.date) }
            .map { entry ->
                val date = entry.key
                val count = entry.value.size
                val totalAmount = entry.value.sumOf { it.amount }
                date to (count to totalAmount)
            }
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val width = width.toFloat()
        val height = height.toFloat()
        val barWidth = width / expenseData.size

        for ((index, data) in expenseData.withIndex()) {
            val (_, pair) = data
            val (count, totalAmount) = pair

            val barHeight = (totalAmount / 1000f) * height
            val countHeight = (count / 10f) * height

            paint.color = Color.BLUE
            canvas.drawRect(
                index * barWidth,
                height - barHeight.toFloat(),
                (index + 1) * barWidth,
                height,
                paint
            )

            paint.color = Color.RED
            canvas.drawRect(
                index * barWidth + barWidth / 4,
                height - countHeight.toFloat(),
                (index + 1) * barWidth - barWidth / 4,
                height,
                paint
            )
        }
    }
}
