package com.yourcompany.loanledger.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.yourcompany.loanledger.data.entity.Line
import com.yourcompany.loanledger.data.entity.LineType
import com.yourcompany.loanledger.repository.LineRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class LineViewModel(private val repository: LineRepository) : ViewModel() {

    val lines: StateFlow<List<Line>> =
        repository.getAllLines()
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun addLine(name: String, type: LineType, investment: Double) {
        if (name.isBlank()) return
        viewModelScope.launch { repository.addLine(name.trim(), type, investment) }
    }

    fun updateInvestment(line: Line, newInvestment: Double) {
        viewModelScope.launch { repository.updateInvestment(line, newInvestment) }
    }

    class Factory(private val repository: LineRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return LineViewModel(repository) as T
        }
    }
}
