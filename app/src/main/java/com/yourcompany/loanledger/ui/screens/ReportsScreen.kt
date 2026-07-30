package com.yourcompany.loanledger.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.yourcompany.loanledger.viewmodel.CollectionViewModel
import com.yourcompany.loanledger.viewmodel.ExpenseViewModel

/**
 * Basic Reports screen — v1 shows the two summaries we have real data for
 * (Daily collection totals, Expense totals). Line Summary / Investment Summary /
 * Missing Customer Summary need multi-line support first (see README TODOs).
 */
@Composable
fun ReportsScreen(collectionViewModel: CollectionViewModel, expenseViewModel: ExpenseViewModel) {
    val pending by collectionViewModel.pendingCollections.collectAsState()
    val completed by collectionViewModel.todayEntries.collectAsState()
    val expenses by expenseViewModel.expenses.collectAsState()

    val todayCollected = completed.filter { it.isPaid }.sumOf { it.amountPaid }
    val totalExpense = expenses.sumOf { it.amount }

    Scaffold(topBar = { TopAppBar(title = { Text("Reports") }) }) { padding ->
        Column(modifier = Modifier.padding(padding).padding(16.dp).fillMaxSize()) {
            ReportCard(title = "Daily Summary") {
                Text("Collected today: ${todayCollected.toInt()}")
                Text("Pending today: ${pending.size} installments")
                Text("Completed entries: ${completed.size}")
            }
            Spacer(modifier = Modifier.height(16.dp))
            ReportCard(title = "Expense Summary") {
                Text("Total expenses: ${totalExpense.toInt()}")
                Text("Entries: ${expenses.size}")
            }
            Spacer(modifier = Modifier.height(16.dp))
            ReportCard(title = "Coming soon") {
                Text("Line Summary, Investment Summary, and Missing Customer")
                Text("Summary need multi-line support — see README TODOs.")
            }
        }
    }
}

@Composable
private fun ReportCard(title: String, content: @Composable ColumnScope.() -> Unit) {
    ElevatedCard(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(title, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            content()
        }
    }
}
