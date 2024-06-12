package com.example.moneymanager

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.moneymanager.widget.Expense
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var addExpenseButton: Button
    private lateinit var viewExpensesButton: Button
    private lateinit var calendarButton: Button
    private lateinit var chartButton: Button
    private val expenses = mutableListOf<Expense>()
    private val categories = listOf("Їжа", "Транспорт", "Розваги", "Інше")
    private val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addExpenseButton = findViewById(R.id.addExpenseButton)
        viewExpensesButton = findViewById(R.id.viewExpensesButton)
        calendarButton = findViewById(R.id.calendarButton)
        chartButton = findViewById(R.id.chartButton)

        addExpenseButton.setOnClickListener {
            showAddExpenseDialog()
        }

        viewExpensesButton.setOnClickListener {
            val intent = Intent(this, ViewExpensesActivity::class.java)
            intent.putParcelableArrayListExtra("expenses", ArrayList(expenses))
            startActivity(intent)
        }

        calendarButton.setOnClickListener {
            val intent = Intent(this, CalendarActivity::class.java)
            intent.putParcelableArrayListExtra("expenses", ArrayList(expenses))
            startActivity(intent)
        }

        chartButton.setOnClickListener {
            val intent = Intent(this, ExpenseChartActivity::class.java)
            intent.putParcelableArrayListExtra("expenses", ArrayList(expenses))
            startActivity(intent)
        }
    }

    private fun showAddExpenseDialog() {
        val dialogView = layoutInflater.inflate(R.layout.add_expense_dialog, null)
        val expenseAmount = dialogView.findViewById<EditText>(R.id.expenseAmount)
        val expenseCategorySpinner = dialogView.findViewById<Spinner>(R.id.expenseCategorySpinner)
        val expenseDateButton = dialogView.findViewById<Button>(R.id.expenseDateButton)
        val saveExpenseButton = dialogView.findViewById<Button>(R.id.saveExpenseButton)

        val categoryAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categories)
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        expenseCategorySpinner.adapter = categoryAdapter

        val calendar = Calendar.getInstance()
        expenseDateButton.text = dateFormat.format(calendar.time)
        expenseDateButton.setOnClickListener {
            DatePickerDialog(this, { _, year, month, dayOfMonth ->
                calendar.set(year, month, dayOfMonth)
                expenseDateButton.text = dateFormat.format(calendar.time)
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show()
        }

        val dialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .create()

        saveExpenseButton.setOnClickListener {
            val amount = expenseAmount.text.toString()
            val category = expenseCategorySpinner.selectedItem.toString()
            val date = calendar.time
            if (amount.isNotEmpty()) {
                val expense = Expense(category, amount.toDouble(), date)
                expenses.add(expense)
                dialog.dismiss()
            }
        }

        dialog.show()
    }
}
