package com.hereliesaz.nolawallet.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.hereliesaz.nolawallet.ui.screens.LicenseDetailScreen
import com.hereliesaz.nolawallet.ui.screens.OnboardingScreen
import com.hereliesaz.nolawallet.ui.screens.PinScreen
import com.hereliesaz.nolawallet.ui.screens.ShareLicenseScreen
import com.hereliesaz.nolawallet.ui.screens.WalletScreen

object Routes {
    const val ONBOARDING = "onboarding"
    const val PIN_ENTRY = "pin_entry"
    const val WALLET = "wallet"
    const val LICENSE_DETAIL = "license_detail"
    const val SHARE_LICENSE = "share_license"
}

@Composable
fun NolaNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.ONBOARDING
    ) {
        // The Entry Point: The smile and the car
        composable(Routes.ONBOARDING) {
            OnboardingScreen()
            // In a real app, clicking "Login" would trigger:
            // navController.navigate(Routes.PIN_ENTRY)
        }

        // The Gatekeeper: 4 digits to prove existence
        composable(Routes.PIN_ENTRY) {
            PinScreen()
            // Success leads to:
            // navController.navigate(Routes.WALLET)
        }

        // The Hub: Your digital leash
        composable(Routes.WALLET) {
            WalletScreen()
            // Clicking the card leads to:
            // navController.navigate(Routes.LICENSE_DETAIL)
        }

        // The Object: The ID Card itself
        composable(Routes.LICENSE_DETAIL) {
            LicenseDetailScreen()
            // Clicking "Share" leads to:
            // navController.navigate(Routes.SHARE_LICENSE)
        }

        // The Verification: The QR Code Face
        composable(Routes.SHARE_LICENSE) {
            ShareLicenseScreen()
        }
    }
}
