package com.yourcompany.loanledger.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.yourcompany.loanledger.data.entity.Customer
import com.yourcompany.loanledger.repository.CustomerRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CustomerViewModel(
    private val repository: CustomerRepository,
    private val lineId: Long
) : ViewModel() {

    val customers: StateFlow<List<Customer>> =
        repository.getCustomersForLine(lineId)
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun addCustomer(name: String, phone: String?) {
        if (name.isBlank()) return
        viewModelScope.launch { repository.addCustomer(lineId, name.trim(), phone?.trim()) }
    }

    fun deleteCustomer(customer: Customer) {
        viewModelScope.launch { repository.deleteCustomer(customer) }
    }

    fun toggleActive(customer: Customer) {
        viewModelScope.launch { repository.updateCustomer(customer.copy(isActive = !customer.isActive)) }
    }

    class Factory(
        private val repository: CustomerRepository,
        private val lineId: Long
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return CustomerViewModel(repository, lineId) as T
        }
    }
}
