package com.hereliesaz.nolawallet

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.hereliesaz.nolawallet.ui.NolaNavigation
import com.hereliesaz.nolawallet.ui.theme.NOLAWalletTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Remove the default action bar to let our Composable TopAppBars take over
        // window.requestFeature(Window.FEATURE_NO_TITLE) 
        // (Usually handled by Theme.NoActionBar in manifest, but good to note)

        setContent {
            NOLAWalletTheme {
                // Begin the simulation
                NolaNavigation()
            }
        }
    }
}
