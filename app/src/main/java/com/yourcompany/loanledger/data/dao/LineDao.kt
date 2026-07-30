package com.yourcompany.loanledger.data.dao

import androidx.room.*
import com.yourcompany.loanledger.data.entity.Line
import kotlinx.coroutines.flow.Flow

@Dao
interface LineDao {
    @Query("SELECT * FROM lines ORDER BY createdAt DESC")
    fun getAllLines(): Flow<List<Line>>

    @Query("SELECT * FROM lines WHERE id = :id")
    suspend fun getLineById(id: Long): Line?

    @Insert
    suspend fun insert(line: Line): Long

    @Update
    suspend fun update(line: Line)
}
