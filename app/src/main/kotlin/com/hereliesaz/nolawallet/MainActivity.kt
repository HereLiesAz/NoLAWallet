package com.hereliesaz.nolawallet

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hereliesaz.nolawallet.data.LicenseRepository
import com.hereliesaz.nolawallet.data.ProjectRepository
import com.hereliesaz.nolawallet.ui.NolaNavigation
import com.hereliesaz.nolawallet.ui.theme.NOLAWalletTheme
import com.hereliesaz.nolawallet.viewmodel.ProjectViewModelFactory
import com.hereliesaz.nolawallet.viewmodel.WalletViewModel
import com.hereliesaz.nolawallet.viewmodel.WalletViewModelFactory

/**
 * The main activity of the application.
 *
 * This activity is the entry point of the application. It sets up the UI and the ViewModel.
 */
class MainActivity : ComponentActivity() {
    /**
     * Called when the activity is first created.
     *
     * This method sets up the UI and the ViewModel.
     *
     * @param savedInstanceState The saved instance state.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Manual Dependency Injection
        val repository = LicenseRepository(applicationContext)
        val viewModelFactory = WalletViewModelFactory(repository)

        val projectRepository = ProjectRepository(applicationContext)
        val projectViewModelFactory = ProjectViewModelFactory(projectRepository)

        setContent {
            NOLAWalletTheme {
                // Pass the factory to the Navigation graph
                NolaNavigation(
                    viewModelFactory = viewModelFactory,
                    projectViewModelFactory = projectViewModelFactory
                )
            }
        }
    }
}
