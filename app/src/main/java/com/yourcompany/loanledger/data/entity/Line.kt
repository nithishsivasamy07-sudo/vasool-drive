package com.yourcompany.loanledger.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

enum class LineType {
    DAILY, WEEKLY, MONTHLY, ENTERPRISE, MONTHLY_INTEREST
}

@Entity(tableName = "lines")
data class Line(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val type: LineType,
    val investment: Double = 0.0,   // starting capital for this line
    val createdAt: Long = System.currentTimeMillis()
)
