package com.yourcompany.loanledger

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.yourcompany.loanledger.data.AppDatabase
import com.yourcompany.loanledger.data.entity.Line
import com.yourcompany.loanledger.repository.*
import com.yourcompany.loanledger.ui.screens.*
import com.yourcompany.loanledger.viewmodel.CollectionViewModel
import com.yourcompany.loanledger.viewmodel.CustomerViewModel
import com.yourcompany.loanledger.viewmodel.ExpenseViewModel
import com.yourcompany.loanledger.viewmodel.LineViewModel

sealed class Screen(val route: String, val label: String, val icon: androidx.compose.ui.graphics.vector.ImageVector) {
    object Collection : Screen("collection", "Collection", Icons.Default.AccountBalanceWallet)
    object Expense : Screen("expense", "Expense", Icons.Default.CreditCard)
    object Customer : Screen("customer", "Customer", Icons.Default.People)
    object Reports : Screen("reports", "Reports", Icons.Default.BarChart)
    object Settings : Screen("settings", "Settings", Icons.Default.Settings)
}

private val bottomNavItems = listOf(
    Screen.Collection, Screen.Expense, Screen.Customer, Screen.Reports, Screen.Settings
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val db = AppDatabase.getInstance(applicationContext)

        setContent {
            MaterialTheme {
                AppRoot(db = db)
            }
        }
    }
}

@Composable
fun AppRoot(db: AppDatabase) {
    val navController: NavHostController = rememberNavController()

    // Repositories — created once, shared across screens
    val collectionRepository = remember { CollectionRepository(db) }
    val customerRepository = remember { CustomerRepository(db) }
    val loanRepository = remember { LoanRepository(db) }
    val expenseRepository = remember { ExpenseRepository(db) }
    val lineRepository = remember { LineRepository(db) }

    // Ensure a Line exists before rendering the rest of the app
    var currentLine by remember { mutableStateOf<Line?>(null) }
    LaunchedEffect(Unit) {
        currentLine = lineRepository.getOrCreateDefaultLine()
    }

    val line = currentLine
    if (line == null) {
        Box(modifier = Modifier) { Text("Loading...") }
        return
    }

    Scaffold(
        bottomBar = { BottomNavBar(navController) }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Collection.route,
            modifier = Modifier.padding(padding)
        ) {
            composable(Screen.Collection.route) {
                val collectionVm: CollectionViewModel = viewModel(
                    factory = CollectionViewModel.Factory(
                        collectionRepository, customerRepository, loanRepository, line.id, line.investment
                    )
                )
                CollectionScreen(viewModel = collectionVm)
            }
            composable(Screen.Expense.route) {
                val expenseVm: ExpenseViewModel = viewModel(
                    factory = ExpenseViewModel.Factory(expenseRepository, line.id)
                )
                ExpenseScreen(viewModel = expenseVm)
            }
            composable(Screen.Customer.route) {
                val customerVm: CustomerViewModel = viewModel(
                    factory = CustomerViewModel.Factory(customerRepository, line.id)
                )
                CustomerScreen(viewModel = customerVm)
            }
            composable(Screen.Reports.route) {
                val collectionVm: CollectionViewModel = viewModel(
                    factory = CollectionViewModel.Factory(
                        collectionRepository, customerRepository, loanRepository, line.id, line.investment
                    )
                )
                val expenseVm: ExpenseViewModel = viewModel(
                    factory = ExpenseViewModel.Factory(expenseRepository, line.id)
                )
                ReportsScreen(collectionViewModel = collectionVm, expenseViewModel = expenseVm)
            }
            composable(Screen.Settings.route) {
                val lineVm: LineViewModel = viewModel(
                    factory = LineViewModel.Factory(lineRepository)
                )
                SettingsScreen(lineViewModel = lineVm)
            }
        }
    }
}

@Composable
fun BottomNavBar(navController: NavHostController) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    NavigationBar {
        bottomNavItems.forEach { screen ->
            NavigationBarItem(
                selected = currentRoute == screen.route,
                onClick = {
                    navController.navigate(screen.route) {
                        launchSingleTop = true
                    }
                },
                icon = { Icon(screen.icon, contentDescription = screen.label) },
                label = { Text(screen.label) }
            )
        }
    }
}
