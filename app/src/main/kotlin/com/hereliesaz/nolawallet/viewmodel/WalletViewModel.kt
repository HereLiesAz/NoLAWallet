package com.hereliesaz.nolawallet.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.hereliesaz.nolawallet.data.IdentityLicense
import com.hereliesaz.nolawallet.data.LicenseRepository
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class WalletViewModel(private val repository: LicenseRepository) : ViewModel() {

    var licenseData by mutableStateOf(IdentityLicense())
        private set

    init {
        // Load data on initialization
        viewModelScope.launch {
            repository.licenseFlow.collectLatest { savedData ->
                licenseData = savedData
            }
        }
    }

    fun updateLicense(newData: IdentityLicense) {
        // Update local state immediately for UI responsiveness
        licenseData = newData
        // Persist to disk
        viewModelScope.launch {
            repository.saveLicense(newData)
        }
    }
}

// Factory to inject the repository without Dagger/Hilt complexity
class WalletViewModelFactory(private val repository: LicenseRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WalletViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return WalletViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
