package com.hereliesaz.nolawallet.data

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

data class WalletItem(
    val id: String,
    val title: String,
    val icon: ImageVector,
    val backgroundColor: Color,
    val contentColor: Color,
    val isVerified: Boolean = false
)

data class IdentityLicense(
    val licenseNumber: String = "",
    val auditNumber: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val dob: String = "",
    val issueDate: String = "",
    val expiryDate: String = "",
    val height: String = "",
    val weight: String = "",
    val eyes: String = "",
    val sex: String = "",
    val addressStreet: String = "",
    val addressCityStateZip: String = "",
    val restrictions: String = "None",
    val endorsements: String = "None",
    val classType: String = "E"
)

// Default empty state
val EmptyLicense = IdentityLicense()
