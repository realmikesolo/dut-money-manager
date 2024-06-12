package com.example.moneymanager

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.moneymanager.widget.Expense

class ViewExpensesActivity : AppCompatActivity() {

    private lateinit var backButton: Button
    private lateinit var expenseListView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_expenses)

        backButton = findViewById(R.id.backButton)
        expenseListView = findViewById(R.id.expenseListView)

        val expenses = intent.getParcelableArrayListExtra<Expense>("expenses") ?: arrayListOf()

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, expenses.map { it.toString() })
        expenseListView.adapter = adapter

        backButton.setOnClickListener {
            finish()
        }
    }
}
