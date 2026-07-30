package com.yourcompany.loanledger.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.yourcompany.loanledger.data.dao.*
import com.yourcompany.loanledger.data.entity.*

@Database(
    entities = [
        Line::class,
        Customer::class,
        Loan::class,
        CollectionEntry::class,
        Expense::class,
        ExpenseType::class,
        Area::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun lineDao(): LineDao
    abstract fun customerDao(): CustomerDao
    abstract fun loanDao(): LoanDao
    abstract fun collectionEntryDao(): CollectionEntryDao
    abstract fun expenseDao(): ExpenseDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "loan_ledger.db"
                ).build().also { INSTANCE = it }
            }
        }
    }
}
