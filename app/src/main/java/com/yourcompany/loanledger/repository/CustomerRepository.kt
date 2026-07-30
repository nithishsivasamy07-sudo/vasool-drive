package com.yourcompany.loanledger.repository

import com.yourcompany.loanledger.data.AppDatabase
import com.yourcompany.loanledger.data.entity.Customer
import kotlinx.coroutines.flow.Flow

class CustomerRepository(private val db: AppDatabase) {

    fun getCustomersForLine(lineId: Long): Flow<List<Customer>> =
        db.customerDao().getCustomersForLine(lineId)

    suspend fun addCustomer(lineId: Long, name: String, phone: String?): Long {
        return db.customerDao().insert(
            Customer(lineId = lineId, name = name, phone = phone)
        )
    }

    suspend fun updateCustomer(customer: Customer) {
        db.customerDao().update(customer)
    }

    suspend fun deleteCustomer(customer: Customer) {
        db.customerDao().delete(customer)
    }
}
