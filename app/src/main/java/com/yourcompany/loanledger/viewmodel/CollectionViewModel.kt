package com.yourcompany.loanledger.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.yourcompany.loanledger.data.entity.CollectionEntry
import com.yourcompany.loanledger.data.entity.Customer
import com.yourcompany.loanledger.repository.CollectionRepository
import com.yourcompany.loanledger.repository.CustomerRepository
import com.yourcompany.loanledger.repository.LoanRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Calendar

enum class CollectionTab { COLLECT, PAY, COMPLETED }

class CollectionViewModel(
    private val repository: CollectionRepository,
    private val customerRepository: CustomerRepository,
    private val loanRepository: LoanRepository,
    private val lineId: Long,
    private val investment: Double
) : ViewModel() {

    val customers: StateFlow<List<Customer>> =
        customerRepository.getCustomersForLine(lineId)
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    /** Creates a new loan for the given customer and generates its installment schedule. */
    fun createLoan(
        customerId: Long,
        principal: Double,
        totalPayable: Double,
        installmentCount: Int,
        frequencyDays: Int
    ) {
        viewModelScope.launch {
            loanRepository.createLoan(customerId, principal, totalPayable, installmentCount, frequencyDays)
        }
    }

    private fun startOfToday(): Long {
        val cal = Calendar.getInstance()
        cal.set(Calendar.HOUR_OF_DAY, 0); cal.set(Calendar.MINUTE, 0)
        cal.set(Calendar.SECOND, 0); cal.set(Calendar.MILLISECOND, 0)
        return cal.timeInMillis
    }

    val pendingCollections: StateFlow<List<CollectionEntry>> =
        repository.getPendingCollections(lineId, startOfToday())
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val todayEntries: StateFlow<List<CollectionEntry>> =
        repository.getEntriesForDate(lineId, startOfToday())
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val balance: StateFlow<Double> =
        repository.getBalance(investment, lineId, startOfToday())
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), investment)

    fun collect(entryId: Long, amount: Double) {
        viewModelScope.launch {
            repository.collectPayment(entryId, amount)
        }
    }

    class Factory(
        private val repository: CollectionRepository,
        private val customerRepository: CustomerRepository,
        private val loanRepository: LoanRepository,
        private val lineId: Long,
        private val investment: Double
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return CollectionViewModel(repository, customerRepository, loanRepository, lineId, investment) as T
        }
    }
}
