package com.yourcompany.loanledger.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.yourcompany.loanledger.data.entity.Expense
import com.yourcompany.loanledger.data.entity.ExpenseType
import com.yourcompany.loanledger.viewmodel.ExpenseViewModel

@Composable
fun ExpenseScreen(viewModel: ExpenseViewModel) {
    val expenses by viewModel.expenses.collectAsState()
    val types by viewModel.expenseTypes.collectAsState()
    var showAddDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Expenses  •  Total: ${expenses.sumOf { it.amount }.toInt()}") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { showAddDialog = true }) {
                Icon(Icons.Default.Add, contentDescription = "Add expense")
            }
        }
    ) { padding ->
        LazyColumn(modifier = Modifier.padding(padding).fillMaxSize()) {
            items(expenses) { expense ->
                val typeName = types.find { it.id == expense.expenseTypeId }?.name ?: "Other"
                ListItem(
                    headlineContent = { Text("$typeName — ${expense.amount.toInt()}") },
                    supportingContent = { expense.note?.let { Text(it) } }
                )
                Divider()
            }
        }
    }

    if (showAddDialog) {
        AddExpenseDialog(
            types = types,
            onAddType = viewModel::addExpenseType,
            onDismiss = { showAddDialog = false },
            onConfirm = { typeId, amount, note ->
                viewModel.addExpense(typeId, amount, note)
                showAddDialog = false
            }
        )
    }
}

@Composable
private fun AddExpenseDialog(
    types: List<ExpenseType>,
    onAddType: (String) -> Unit,
    onDismiss: () -> Unit,
    onConfirm: (Long, Double, String?) -> Unit
) {
    var amount by remember { mutableStateOf("") }
    var note by remember { mutableStateOf("") }
    var newTypeName by remember { mutableStateOf("") }
    var selectedTypeId by remember { mutableStateOf(types.firstOrNull()?.id) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add Expense") },
        text = {
            Column {
                if (types.isEmpty()) {
                    Text("No expense types yet — create one:")
                    OutlinedTextField(
                        value = newTypeName,
                        onValueChange = { newTypeName = it },
                        label = { Text("Type name e.g. Fuel, Food") }
                    )
                    Button(onClick = { onAddType(newTypeName); newTypeName = "" }) {
                        Text("Add Type")
                    }
                } else {
                    Text("Type: ${types.find { it.id == selectedTypeId }?.name ?: "Select"}")
                    // Simple selector — for a fuller UI, replace with DropdownMenu
                    types.forEach { type ->
                        TextButton(onClick = { selectedTypeId = type.id }) { Text(type.name) }
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(value = amount, onValueChange = { amount = it }, label = { Text("Amount") })
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(value = note, onValueChange = { note = it }, label = { Text("Note (optional)") })
            }
        },
        confirmButton = {
            TextButton(onClick = {
                val amt = amount.toDoubleOrNull() ?: 0.0
                selectedTypeId?.let { onConfirm(it, amt, note.ifBlank { null }) }
            }) { Text("Save") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        }
    )
}
