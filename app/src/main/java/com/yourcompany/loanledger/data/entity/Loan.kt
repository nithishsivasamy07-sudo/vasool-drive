package com.yourcompany.loanledger.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "loans",
    foreignKeys = [
        ForeignKey(
            entity = Customer::class,
            parentColumns = ["id"],
            childColumns = ["customerId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Loan(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val customerId: Long,
    val principal: Double,          // amount given (Payable base, before interest)
    val totalPayable: Double,       // principal + interest = "Payable"/"Total"
    val installmentCount: Int,      // e.g. 10 installments
    val installmentAmount: Double,
    val startDate: Long,
    val isClosed: Boolean = false
)
