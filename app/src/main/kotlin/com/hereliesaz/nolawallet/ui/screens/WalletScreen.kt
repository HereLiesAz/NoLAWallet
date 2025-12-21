package com.hereliesaz.nolawallet.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.* // Import all
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.* // Import all
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.hereliesaz.nolawallet.R
import com.hereliesaz.nolawallet.ui.theme.StateBlue
import com.hereliesaz.nolawallet.viewmodel.WalletViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WalletScreen(
    viewModel: WalletViewModel,
    onCardClick: () -> Unit,
    onSecretTrigger: () -> Unit,
    onNewProjectClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        // Using the official title asset
                        Image(
                            painter = rememberAsyncImagePainter(model = "file:///android_res/drawable/hometitle.png"),
                            contentDescription = "NOLA Wallet",
                            modifier = Modifier
                                .height(24.dp)
                                .clickable { onSecretTrigger() } // Secret Trigger moved to logo click
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = StateBlue)
            )
        },
        bottomBar = {
            BottomAppBar(containerColor = StateBlue, contentColor = Color.White) {
                // Using specific drawable assets for nav
                // Note: You need to ensure these PNGs are in res/drawable with these names
                BottomNavImageItem("Home", "tabhome", true)
                BottomNavImageItem("Verify", "tabverifyyou", false)
                BottomNavImageItem("Scan", "tabscan", false)
                BottomNavImageItem("Settings", "tabsettings", false) // Assuming settings icon exists
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(StateBlue)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp) // increased spacing
        ) {
            // The Licenses Card
            // We use the 'licenseback.png' from resources
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .clickable { onCardClick() }
            ) {
                Image(
                    painter = rememberAsyncImagePainter(model = "file:///android_res/drawable/licenseback.png"),
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier.fillMaxSize()
                )
                
                // Status Overlay (Valid)
                Image(
                    painter = rememberAsyncImagePainter(model = "file:///android_res/drawable/verifyyouvalid.png"),
                    contentDescription = "Valid",
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(12.dp)
                        .size(80.dp, 30.dp) // Adjust based on asset aspect ratio
                )
                
                // User Name Overlay
                Text(
                    text = viewModel.licenseData.firstName + " " + viewModel.licenseData.lastName,
                    color = Color.White,
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(16.dp)
                )
            }

            // Wildlife / Hunting License
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.White) // Fallback
            ) {
                 Image(
                    painter = rememberAsyncImagePainter(model = "file:///android_res/drawable/wildlifelogo.png"),
                    contentDescription = "Wildlife",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.align(Alignment.CenterStart).padding(16.dp)
                )
                // Text details...
            }

            // Create New Project Button
            Button(
                onClick = onNewProjectClick,
                modifier = Modifier.fillMaxWidth().height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = StateBlue)
            ) {
                Text("Create New Project")
            }
        }
    }
}

@Composable
fun RowScope.BottomNavImageItem(label: String, resourceName: String, selected: Boolean) {
    NavigationBarItem(
        selected = selected,
        onClick = {},
        icon = {
            Image(
                painter = rememberAsyncImagePainter(model = "file:///android_res/drawable/$resourceName.png"),
                contentDescription = label,
                modifier = Modifier.size(28.dp),
                alpha = if (selected) 1f else 0.6f
            )
        },
        colors = NavigationBarItemDefaults.colors(indicatorColor = Color.Transparent)
    )
}
