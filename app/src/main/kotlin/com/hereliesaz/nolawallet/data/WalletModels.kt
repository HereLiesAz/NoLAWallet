package com.hereliesaz.nolawallet.data

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * A data class that represents a wallet item.
 *
 * @param id The ID of the wallet item.
 * @param title The title of the wallet item.
 * @param icon The icon of the wallet item.
 * @param backgroundColor The background color of the wallet item.
 * @param contentColor The content color of the wallet item.
 * @param isVerified Whether the wallet item is verified.
 */
data class WalletItem(
    val id: String,
    val title: String,
    val icon: ImageVector,
    val backgroundColor: Color,
    val contentColor: Color,
    val isVerified: Boolean = false
)

/**
 * A data class that represents an identity license.
 *
 * @param licenseNumber The license number.
 */
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
    val classType: String = "E",
    val photoPath: String = "" // The path to the stored image file
)

// Default empty state
val EmptyLicense = IdentityLicense()
