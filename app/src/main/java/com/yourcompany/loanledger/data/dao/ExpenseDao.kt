package com.yourcompany.loanledger.data.dao

import androidx.room.*
import com.yourcompany.loanledger.data.entity.Expense
import com.yourcompany.loanledger.data.entity.ExpenseType
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpenseDao {
    @Query("SELECT * FROM expenses WHERE lineId = :lineId ORDER BY date DESC")
    fun getExpensesForLine(lineId: Long): Flow<List<Expense>>

    @Query("SELECT SUM(amount) FROM expenses WHERE lineId = :lineId")
    fun getTotalExpense(lineId: Long): Flow<Double?>

    @Insert
    suspend fun insert(expense: Expense): Long

    @Query("SELECT * FROM expense_types")
    fun getAllExpenseTypes(): Flow<List<ExpenseType>>

    @Insert
    suspend fun insertExpenseType(type: ExpenseType): Long
}
