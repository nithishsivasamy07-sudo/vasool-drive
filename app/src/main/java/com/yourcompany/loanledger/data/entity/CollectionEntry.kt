package com.yourcompany.loanledger.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "collection_entries",
    foreignKeys = [
        ForeignKey(
            entity = Loan::class,
            parentColumns = ["id"],
            childColumns = ["loanId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class CollectionEntry(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val loanId: Long,
    val dueDate: Long,          // the date this installment was due
    val installmentIndex: Int,  // e.g. 1 of 10
    val amountPaid: Double = 0.0,
    val isPaid: Boolean = false,
    val paidAt: Long? = null
)
