package com.yourcompany.loanledger.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.yourcompany.loanledger.data.entity.LineType
import com.yourcompany.loanledger.viewmodel.LineViewModel

private val settingsItems = listOf(
    "Line", "Area", "Expense Type", "Backup"
    // "Import Line" / "Export Line" / "License" / "Support" — cloud/paid features,
    // intentionally out of scope for this local-only v1. See README.
)

@Composable
fun SettingsScreen(lineViewModel: LineViewModel) {
    var showLineManager by remember { mutableStateOf(false) }

    if (showLineManager) {
        LineManagerScreen(viewModel = lineViewModel, onBack = { showLineManager = false })
        return
    }

    Scaffold(topBar = { TopAppBar(title = { Text("Settings") }) }) { padding ->
        LazyColumn(modifier = Modifier.padding(padding).fillMaxSize()) {
            items(settingsItems) { item ->
                ListItem(
                    headlineContent = { Text(item) },
                    modifier = Modifier.clickable {
                        if (item == "Line") showLineManager = true
                        // Area / Expense Type / Backup — TODO: build similarly to Line manager
                    }
                )
                Divider()
            }
        }
    }
}

@Composable
private fun LineManagerScreen(viewModel: LineViewModel, onBack: () -> Unit) {
    val lines by viewModel.lines.collectAsState()
    var name by remember { mutableStateOf("") }
    var investment by remember { mutableStateOf("") }
    var selectedType by remember { mutableStateOf(LineType.DAILY) }

    Scaffold(topBar = {
        TopAppBar(
            title = { Text("Manage Lines") },
            navigationIcon = { TextButton(onClick = onBack) { Text("Back") } }
        )
    }) { padding ->
        Column(modifier = Modifier.padding(padding).fillMaxSize().padding(16.dp)) {
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(lines) { line ->
                    ListItem(
                        headlineContent = { Text(line.name) },
                        supportingContent = { Text("${line.type} • Investment: ${line.investment.toInt()}") }
                    )
                    Divider()
                }
            }

            Text("Add New Line", style = MaterialTheme.typography.titleMedium)
            OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Line name") })
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(value = investment, onValueChange = { investment = it }, label = { Text("Investment") })
            Spacer(modifier = Modifier.height(8.dp))
            Row {
                LineType.values().forEach { type ->
                    FilterChip(
                        selected = selectedType == type,
                        onClick = { selectedType = type },
                        label = { Text(type.name) },
                        modifier = Modifier.padding(end = 4.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = {
                    viewModel.addLine(name, selectedType, investment.toDoubleOrNull() ?: 0.0)
                    name = ""; investment = ""
                },
                modifier = Modifier.fillMaxWidth()
            ) { Text("Save Line") }
        }
    }
}
