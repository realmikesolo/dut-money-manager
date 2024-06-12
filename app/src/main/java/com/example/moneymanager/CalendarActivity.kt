package com.example.moneymanager

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CalendarView
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.moneymanager.widget.Expense
import java.text.SimpleDateFormat
import java.util.*

class CalendarActivity : AppCompatActivity() {

    private lateinit var backButton: Button
    private lateinit var calendarView: CalendarView
    private lateinit var expenseListView: ListView
    private val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.calendar_view)

        backButton = findViewById(R.id.backButton)
        calendarView = findViewById(R.id.calendarView)
        expenseListView = findViewById(R.id.expenseListView)

        val expenses = intent.getParcelableArrayListExtra<Expense>("expenses") ?: arrayListOf()

        backButton.setOnClickListener {
            finish()
        }

        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val selectedDate = Calendar.getInstance()
            selectedDate.set(year, month, dayOfMonth)
            val selectedExpenses = expenses.filter {
                dateFormat.format(it.date) == dateFormat.format(selectedDate.time)
            }
            val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, selectedExpenses.map { it.toString() })
            expenseListView.adapter = adapter

            if (selectedExpenses.isEmpty()) {
                Toast.makeText(this, "Немає витрат за обрану дату", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
