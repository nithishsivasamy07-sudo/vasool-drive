package com.yourcompany.loanledger.data.dao

import androidx.room.*
import com.yourcompany.loanledger.data.entity.CollectionEntry
import kotlinx.coroutines.flow.Flow

@Dao
interface CollectionEntryDao {

    // "COMPLETED" tab: today's entries, paid or not
    @Query("SELECT * FROM collection_entries WHERE dueDate = :date AND loanId IN (SELECT id FROM loans WHERE customerId IN (SELECT id FROM customers WHERE lineId = :lineId))")
    fun getEntriesForDate(lineId: Long, date: Long): Flow<List<CollectionEntry>>

    // "COLLECT" tab: entries not yet paid, due today or overdue
    @Query("SELECT * FROM collection_entries WHERE isPaid = 0 AND dueDate <= :today AND loanId IN (SELECT id FROM loans WHERE customerId IN (SELECT id FROM customers WHERE lineId = :lineId)) ORDER BY dueDate ASC")
    fun getPendingCollections(lineId: Long, today: Long): Flow<List<CollectionEntry>>

    @Query("SELECT SUM(amountPaid) FROM collection_entries WHERE dueDate = :date AND loanId IN (SELECT id FROM loans WHERE customerId IN (SELECT id FROM customers WHERE lineId = :lineId))")
    fun getTodayCollectionTotal(lineId: Long, date: Long): Flow<Double?>

    @Insert
    suspend fun insert(entry: CollectionEntry): Long

    @Update
    suspend fun update(entry: CollectionEntry)

    @Query("UPDATE collection_entries SET amountPaid = :amount, isPaid = 1, paidAt = :paidAt WHERE id = :id")
    suspend fun markPaid(id: Long, amount: Double, paidAt: Long)
}
