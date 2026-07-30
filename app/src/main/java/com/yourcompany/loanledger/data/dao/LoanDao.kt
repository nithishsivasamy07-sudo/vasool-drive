package com.yourcompany.loanledger.data.dao

import androidx.room.*
import com.yourcompany.loanledger.data.entity.Loan
import kotlinx.coroutines.flow.Flow

@Dao
interface LoanDao {
    @Query("SELECT * FROM loans WHERE customerId = :customerId AND isClosed = 0 LIMIT 1")
    suspend fun getActiveLoanForCustomer(customerId: Long): Loan?

    @Query("SELECT * FROM loans WHERE id = :id")
    suspend fun getLoanById(id: Long): Loan?

    @Query("SELECT * FROM loans WHERE customerId IN (SELECT id FROM customers WHERE lineId = :lineId) AND isClosed = 0")
    fun getActiveLoansForLine(lineId: Long): Flow<List<Loan>>

    @Insert
    suspend fun insert(loan: Loan): Long

    @Update
    suspend fun update(loan: Loan)
}
