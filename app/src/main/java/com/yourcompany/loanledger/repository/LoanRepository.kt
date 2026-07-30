package com.yourcompany.loanledger.repository

import com.yourcompany.loanledger.data.AppDatabase
import com.yourcompany.loanledger.data.entity.CollectionEntry
import com.yourcompany.loanledger.data.entity.Loan
import java.util.Calendar

class LoanRepository(private val db: AppDatabase) {

    /**
     * Creates a loan for a customer and generates its installment schedule
     * (one CollectionEntry per installment, spaced by frequency).
     */
    suspend fun createLoan(
        customerId: Long,
        principal: Double,
        totalPayable: Double,
        installmentCount: Int,
        frequencyDays: Int, // 1 = daily, 7 = weekly, 30 = monthly
        startDate: Long = System.currentTimeMillis()
    ): Long {
        val installmentAmount = totalPayable / installmentCount
        val loanId = db.loanDao().insert(
            Loan(
                customerId = customerId,
                principal = principal,
                totalPayable = totalPayable,
                installmentCount = installmentCount,
                installmentAmount = installmentAmount,
                startDate = startDate
            )
        )

        val cal = Calendar.getInstance().apply { timeInMillis = startDate }
        for (i in 1..installmentCount) {
            db.collectionEntryDao().insert(
                CollectionEntry(
                    loanId = loanId,
                    dueDate = cal.timeInMillis,
                    installmentIndex = i,
                    amountPaid = 0.0,
                    isPaid = false
                )
            )
            cal.add(Calendar.DAY_OF_YEAR, frequencyDays)
        }
        return loanId
    }
}
