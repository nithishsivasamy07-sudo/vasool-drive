package com.yourcompany.loanledger.repository

import com.yourcompany.loanledger.data.AppDatabase
import com.yourcompany.loanledger.data.entity.Expense
import com.yourcompany.loanledger.data.entity.ExpenseType
import kotlinx.coroutines.flow.Flow

class ExpenseRepository(private val db: AppDatabase) {

    fun getExpensesForLine(lineId: Long): Flow<List<Expense>> =
        db.expenseDao().getExpensesForLine(lineId)

    fun getExpenseTypes(): Flow<List<ExpenseType>> =
        db.expenseDao().getAllExpenseTypes()

    suspend fun addExpenseType(name: String): Long =
        db.expenseDao().insertExpenseType(ExpenseType(name = name))

    suspend fun addExpense(lineId: Long, expenseTypeId: Long, amount: Double, note: String?) {
        db.expenseDao().insert(
            Expense(lineId = lineId, expenseTypeId = expenseTypeId, amount = amount, note = note)
        )
    }
}
