package com.yourcompany.loanledger.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.yourcompany.loanledger.data.entity.Customer
import com.yourcompany.loanledger.viewmodel.CustomerViewModel

@Composable
fun CustomerScreen(viewModel: CustomerViewModel) {
    val customers by viewModel.customers.collectAsState()
    var showAddDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Total Customer: ${customers.size}") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { showAddDialog = true }) {
                Icon(Icons.Default.Add, contentDescription = "Add customer")
            }
        }
    ) { padding ->
        LazyColumn(modifier = Modifier.padding(padding).fillMaxSize()) {
            items(customers) { customer ->
                CustomerRow(
                    customer = customer,
                    onToggleActive = { viewModel.toggleActive(customer) },
                    onDelete = { viewModel.deleteCustomer(customer) }
                )
                Divider()
            }
        }
    }

    if (showAddDialog) {
        AddCustomerDialog(
            onDismiss = { showAddDialog = false },
            onConfirm = { name, phone ->
                viewModel.addCustomer(name, phone)
                showAddDialog = false
            }
        )
    }
}

@Composable
private fun CustomerRow(customer: Customer, onToggleActive: () -> Unit, onDelete: () -> Unit) {
    ListItem(
        headlineContent = { Text("Name: ${customer.name}") },
        supportingContent = {
            Text(
                text = "Status: ${if (customer.isActive) "Active" else "Inactive"}",
            )
        },
        trailingContent = {
            Row {
                TextButton(onClick = onToggleActive) {
                    Text(if (customer.isActive) "Deactivate" else "Activate")
                }
                IconButton(onClick = onDelete) {
                    Icon(Icons.Default.Delete, contentDescription = "Delete")
                }
            }
        }
    )
}

@Composable
private fun AddCustomerDialog(onDismiss: () -> Unit, onConfirm: (String, String?) -> Unit) {
    var name by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add Customer") },
        text = {
            Column {
                OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Name") })
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(value = phone, onValueChange = { phone = it }, label = { Text("Phone (optional)") })
            }
        },
        confirmButton = {
            TextButton(onClick = { onConfirm(name, phone.ifBlank { null }) }) { Text("Save") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        }
    )
}
