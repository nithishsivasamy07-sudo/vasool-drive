package com.yourcompany.loanledger.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "expense_types")
data class ExpenseType(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String
)

@Entity(tableName = "expenses")
data class Expense(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val lineId: Long,
    val expenseTypeId: Long,
    val amount: Double,
    val note: String? = null,
    val date: Long = System.currentTimeMillis()
)
