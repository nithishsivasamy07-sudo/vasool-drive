package com.yourcompany.loanledger.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.yourcompany.loanledger.data.entity.Expense
import com.yourcompany.loanledger.data.entity.ExpenseType
import com.yourcompany.loanledger.repository.ExpenseRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ExpenseViewModel(
    private val repository: ExpenseRepository,
    private val lineId: Long
) : ViewModel() {

    val expenses: StateFlow<List<Expense>> =
        repository.getExpensesForLine(lineId)
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val expenseTypes: StateFlow<List<ExpenseType>> =
        repository.getExpenseTypes()
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun addExpenseType(name: String) {
        if (name.isBlank()) return
        viewModelScope.launch { repository.addExpenseType(name.trim()) }
    }

    fun addExpense(expenseTypeId: Long, amount: Double, note: String?) {
        if (amount <= 0.0) return
        viewModelScope.launch { repository.addExpense(lineId, expenseTypeId, amount, note) }
    }

    class Factory(
        private val repository: ExpenseRepository,
        private val lineId: Long
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return ExpenseViewModel(repository, lineId) as T
        }
    }
}
