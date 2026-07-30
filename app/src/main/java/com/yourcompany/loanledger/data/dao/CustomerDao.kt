package com.yourcompany.loanledger.data.dao

import androidx.room.*
import com.yourcompany.loanledger.data.entity.Customer
import kotlinx.coroutines.flow.Flow

@Dao
interface CustomerDao {
    @Query("SELECT * FROM customers WHERE lineId = :lineId ORDER BY sortOrder ASC")
    fun getCustomersForLine(lineId: Long): Flow<List<Customer>>

    @Query("SELECT * FROM customers WHERE id = :id")
    suspend fun getCustomerById(id: Long): Customer?

    @Query("SELECT COUNT(*) FROM customers WHERE lineId = :lineId AND isActive = 1")
    fun getActiveCustomerCount(lineId: Long): Flow<Int>

    @Insert
    suspend fun insert(customer: Customer): Long

    @Update
    suspend fun update(customer: Customer)

    @Delete
    suspend fun delete(customer: Customer)

    @Query("UPDATE customers SET sortOrder = :order WHERE id = :id")
    suspend fun updateSortOrder(id: Long, order: Int)
}
