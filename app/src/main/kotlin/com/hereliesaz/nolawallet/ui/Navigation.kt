package com.hereliesaz.nolawallet.ui

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.hereliesaz.nolawallet.ui.screens.LicenseDetailScreen
import com.hereliesaz.nolawallet.ui.screens.OnboardingScreen
import com.hereliesaz.nolawallet.ui.screens.PinScreen
import com.hereliesaz.nolawallet.ui.screens.SecretConfigScreen
import com.hereliesaz.nolawallet.ui.screens.ShareLicenseScreen
import com.hereliesaz.nolawallet.ui.screens.WalletScreen
import com.hereliesaz.nolawallet.viewmodel.WalletViewModel

/**
 * A sealed class that contains the routes for the application.
 */
object Routes {
    /**
     * The route for the onboarding screen.
     */
    const val ONBOARDING = "onboarding"
    /**
     * The route for the PIN entry screen.
     */
    const val PIN_ENTRY = "pin_entry"
    /**
     * The route for the wallet screen.
     */
    const val WALLET = "wallet"
    /**
     * The route for the license detail screen.
     */
    const val LICENSE_DETAIL = "license_detail"
    /**
     * The route for the share license screen.
     */
    const val SHARE_LICENSE = "share_license"
    /**
     * The route for the secret config screen.
     */
    const val SECRET_CONFIG = "secret_config"
}

/**
 * The navigation graph for the application.
 *
 * @param viewModelFactory The ViewModel factory.
 */
@Composable
fun NolaNavigation(viewModelFactory: androidx.lifecycle.ViewModelProvider.Factory) {
    val navController = rememberNavController()
    // Use the factory to get the repository-backed ViewModel
    val walletViewModel: WalletViewModel = viewModel(factory = viewModelFactory)

    NavHost(
        navController = navController,
        startDestination = Routes.ONBOARDING
    ) {
        composable(Routes.ONBOARDING) {
            OnboardingScreen(
                onLogin = { navController.navigate(Routes.PIN_ENTRY) }
            )
        }

        composable(Routes.PIN_ENTRY) {
            PinScreen(
                onPinSuccess = { navController.navigate(Routes.WALLET) }
            )
        }

        composable(Routes.WALLET) {
            WalletScreen(
                viewModel = walletViewModel,
                onCardClick = { navController.navigate(Routes.LICENSE_DETAIL) },
                // The secret trigger action
                onSecretTrigger = { navController.navigate(Routes.SECRET_CONFIG) }
            )
        }

        composable(Routes.LICENSE_DETAIL) {
            LicenseDetailScreen(
                viewModel = walletViewModel,
                onShareClick = { navController.navigate(Routes.SHARE_LICENSE) },
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(Routes.SHARE_LICENSE) {
            ShareLicenseScreen(
                viewModel = walletViewModel,
                onBackClick = { navController.popBackStack() }
            )
        }
        
        // The Secret Room
        composable(Routes.SECRET_CONFIG) {
            SecretConfigScreen(
                viewModel = walletViewModel,
                navController = navController
            )
        }
    }
}
