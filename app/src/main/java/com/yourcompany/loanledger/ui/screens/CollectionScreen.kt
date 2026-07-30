package com.yourcompany.loanledger.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.yourcompany.loanledger.viewmodel.CollectionTab
import com.yourcompany.loanledger.viewmodel.CollectionViewModel

@Composable
fun CollectionScreen(viewModel: CollectionViewModel) {
    var selectedTab by remember { mutableStateOf(CollectionTab.COLLECT) }
    val balance by viewModel.balance.collectAsState()
    val pending by viewModel.pendingCollections.collectAsState()
    val completed by viewModel.todayEntries.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {

        // --- Summary header: Investment / Expense / Collection / Balance ---
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                SummaryLine(label = "Investment", value = "20000", color = Color(0xFF2E7D32))
                SummaryLine(label = "Collection", value = "1000", color = Color(0xFF2E7D32))
            }
            Column {
                SummaryLine(label = "Expense", value = "500", color = Color(0xFFC62828))
                SummaryLine(label = "Balance", value = balance.toInt().toString(), color = Color(0xFF2E7D32))
            }
        }

        // --- Tabs: COLLECT / PAY / COMPLETED ---
        TabRow(selectedTabIndex = selectedTab.ordinal) {
            CollectionTab.values().forEach { tab ->
                Tab(
                    selected = selectedTab == tab,
                    onClick = { selectedTab = tab },
                    text = { Text(tab.name) }
                )
            }
        }

        val customers by viewModel.customers.collectAsState()

        when (selectedTab) {
            CollectionTab.COLLECT -> CollectList(entries = pending, onCollect = viewModel::collect)
            CollectionTab.PAY -> PayTab(
                customers = customers,
                onCreateLoan = { customerId, principal, totalPayable, count, freqDays ->
                    viewModel.createLoan(customerId, principal, totalPayable, count, freqDays)
                }
            )
            CollectionTab.COMPLETED -> CompletedList(entries = completed)
        }
    }
}

@Composable
private fun SummaryLine(label: String, value: String, color: Color) {
    Row {
        Text("$label: ")
        Text(value, color = color)
    }
}

@Composable
private fun CollectList(
    entries: List<com.yourcompany.loanledger.data.entity.CollectionEntry>,
    onCollect: (Long, Double) -> Unit
) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(entries) { entry ->
            ListItem(
                headlineContent = { Text("Installment #${entry.installmentIndex}") },
                supportingContent = { Text("Due: ${entry.dueDate}") },
                trailingContent = {
                    Button(onClick = { onCollect(entry.id, entry.amountPaid) }) {
                        Text("Collect")
                    }
                }
            )
            Divider()
        }
    }
}

@Composable
private fun CompletedList(entries: List<com.yourcompany.loanledger.data.entity.CollectionEntry>) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(entries) { entry ->
            ListItem(
                headlineContent = { Text("Installment #${entry.installmentIndex}") },
                supportingContent = { Text(if (entry.isPaid) "PAID" else "NOT PAID") }
            )
            Divider()
        }
    }
}

@Composable
private fun PayTab(
    customers: List<com.yourcompany.loanledger.data.entity.Customer>,
    onCreateLoan: (customerId: Long, principal: Double, totalPayable: Double, count: Int, freqDays: Int) -> Unit
) {
    var selectedCustomerId by remember { mutableStateOf(customers.firstOrNull()?.id) }
    var principal by remember { mutableStateOf("") }
    var totalPayable by remember { mutableStateOf("") }
    var installments by remember { mutableStateOf("") }
    var frequency by remember { mutableStateOf(1) } // 1=daily, 7=weekly, 30=monthly

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Select Customer", style = MaterialTheme.typography.titleMedium)
        Row(modifier = Modifier.fillMaxWidth()) {
            customers.forEach { c ->
                FilterChip(
                    selected = selectedCustomerId == c.id,
                    onClick = { selectedCustomerId = c.id },
                    label = { Text(c.name) },
                    modifier = Modifier.padding(end = 8.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = principal, onValueChange = { principal = it },
            label = { Text("Principal (amount given)") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = totalPayable, onValueChange = { totalPayable = it },
            label = { Text("Total Payable (with interest)") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = installments, onValueChange = { installments = it },
            label = { Text("Number of installments") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        Text("Frequency")
        Row {
            listOf("Daily" to 1, "Weekly" to 7, "Monthly" to 30).forEach { (label, days) ->
                FilterChip(
                    selected = frequency == days,
                    onClick = { frequency = days },
                    label = { Text(label) },
                    modifier = Modifier.padding(end = 8.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val cid = selectedCustomerId ?: return@Button
                val p = principal.toDoubleOrNull() ?: return@Button
                val tp = totalPayable.toDoubleOrNull() ?: return@Button
                val count = installments.toIntOrNull() ?: return@Button
                onCreateLoan(cid, p, tp, count, frequency)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Disburse Loan")
        }

        if (customers.isEmpty()) {
            Spacer(modifier = Modifier.height(16.dp))
            Text("No customers yet — add one from the Customer tab first.")
        }
    }
}
