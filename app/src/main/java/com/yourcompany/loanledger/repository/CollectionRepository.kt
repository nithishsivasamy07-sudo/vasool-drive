package com.yourcompany.loanledger.repository

import com.yourcompany.loanledger.data.AppDatabase
import com.yourcompany.loanledger.data.entity.CollectionEntry
import com.yourcompany.loanledger.data.entity.Customer
import com.yourcompany.loanledger.data.entity.Loan
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

/**
 * Mirrors the dashboard math seen on the Collection screen:
 * Balance = Investment - Expense + Collection
 */
class CollectionRepository(private val db: AppDatabase) {

    fun getCustomersForLine(lineId: Long): Flow<List<Customer>> =
        db.customerDao().getCustomersForLine(lineId)

    fun getPendingCollections(lineId: Long, today: Long): Flow<List<CollectionEntry>> =
        db.collectionEntryDao().getPendingCollections(lineId, today)

    fun getEntriesForDate(lineId: Long, date: Long): Flow<List<CollectionEntry>> =
        db.collectionEntryDao().getEntriesForDate(lineId, date)

    fun getTodayCollectionTotal(lineId: Long, date: Long): Flow<Double?> =
        db.collectionEntryDao().getTodayCollectionTotal(lineId, date)

    fun getTotalExpense(lineId: Long): Flow<Double?> =
        db.expenseDao().getTotalExpense(lineId)

    suspend fun getLoanById(loanId: Long): Loan? = db.loanDao().getLoanById(loanId)

    suspend fun collectPayment(entryId: Long, amount: Double) {
        db.collectionEntryDao().markPaid(entryId, amount, System.currentTimeMillis())
    }

    /** Balance = investment - totalExpense + totalCollection (for a given line) */
    fun getBalance(investment: Double, lineId: Long, today: Long): Flow<Double> =
        combine(
            getTotalExpense(lineId),
            getTodayCollectionTotal(lineId, today)
        ) { expense, collection ->
            investment - (expense ?: 0.0) + (collection ?: 0.0)
        }
}
