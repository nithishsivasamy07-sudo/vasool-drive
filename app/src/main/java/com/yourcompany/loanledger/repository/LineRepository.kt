package com.yourcompany.loanledger.repository

import com.yourcompany.loanledger.data.AppDatabase
import com.yourcompany.loanledger.data.entity.Line
import com.yourcompany.loanledger.data.entity.LineType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class LineRepository(private val db: AppDatabase) {

    fun getAllLines(): Flow<List<Line>> = db.lineDao().getAllLines()

    suspend fun addLine(name: String, type: LineType, investment: Double): Long =
        db.lineDao().insert(Line(name = name, type = type, investment = investment))

    suspend fun updateInvestment(line: Line, newInvestment: Double) {
        db.lineDao().update(line.copy(investment = newInvestment))
    }

    /** Ensures at least one Line exists so the app isn't empty on first launch. */
    suspend fun getOrCreateDefaultLine(): Line {
        val existing = db.lineDao().getAllLines().first()
        if (existing.isNotEmpty()) return existing.first()
        val id = addLine(name = "Default Line", type = LineType.DAILY, investment = 0.0)
        return db.lineDao().getLineById(id)!!
    }
}
