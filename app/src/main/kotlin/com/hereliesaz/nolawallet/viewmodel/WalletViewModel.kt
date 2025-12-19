package com.hereliesaz.nolawallet.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.hereliesaz.nolawallet.data.IdentityLicense

class WalletViewModel : ViewModel() {
    // We use a simple mutable state for this simulation.
    // In a real app, this would be encrypted SharedPreferences or DataStore.
    var licenseData by mutableStateOf(IdentityLicense())
        private set

    fun updateLicense(newData: IdentityLicense) {
        licenseData = newData
    }
}
