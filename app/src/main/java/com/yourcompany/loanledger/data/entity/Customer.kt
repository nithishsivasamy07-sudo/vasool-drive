package com.yourcompany.loanledger.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "customers",
    foreignKeys = [
        ForeignKey(
            entity = Line::class,
            parentColumns = ["id"],
            childColumns = ["lineId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Customer(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val lineId: Long,
    val name: String,
    val phone: String? = null,
    val address: String? = null,
    val isActive: Boolean = true,
    val sortOrder: Int = 0
)
