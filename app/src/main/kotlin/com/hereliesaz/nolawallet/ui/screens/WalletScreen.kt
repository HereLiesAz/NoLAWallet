package com.hereliesaz.nolawallet.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocalHospital
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hereliesaz.nolawallet.R
import com.hereliesaz.nolawallet.ui.theme.GoldAccent
import com.hereliesaz.nolawallet.ui.theme.HandgunGold
import com.hereliesaz.nolawallet.ui.theme.HealthGrey
import com.hereliesaz.nolawallet.ui.theme.LicenseGreen
import com.hereliesaz.nolawallet.ui.theme.LicenseOverlayBlue
import com.hereliesaz.nolawallet.ui.theme.StateBlue
import com.hereliesaz.nolawallet.ui.theme.TextBlack
import com.hereliesaz.nolawallet.ui.theme.TextWhite
import com.hereliesaz.nolawallet.ui.theme.TsaBlue
import com.hereliesaz.nolawallet.ui.theme.VehicleBlue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WalletScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        // Placeholder for the LA Map Logo
                        // Icon(painter = painterResource(id = R.drawable.ic_la_map), contentDescription = null, tint = GoldAccent)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "NOLA Wallet", // Rebranded as requested
                            color = TextWhite,
                            fontWeight = FontWeight.Bold
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = StateBlue
                )
            )
        },
        bottomBar = {
            BottomAppBar(
                containerColor = StateBlue,
                contentColor = TextWhite
            ) {
                BottomNavItem("Home", Icons.Default.Home, true)
                BottomNavItem("Share", Icons.Default.Share, false)
                BottomNavItem("Scan", Icons.Default.QrCodeScanner, false)
                BottomNavItem("Menu", Icons.Default.Menu, false)
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(StateBlue)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // The Scrollable list of bureaucratic tokens

            WalletItemCard(
                text = "Concealed Handgun Permit",
                icon = Icons.Default.Shield, // Placeholder icon
                backgroundColor = HandgunGold,
                textColor = TextBlack
            )

            WalletItemCard(
                text = "My Vehicles",
                icon = Icons.Default.DirectionsCar,
                backgroundColor = VehicleBlue,
                textColor = TextWhite
            )

            WalletItemCard(
                text = "Medicaid Health Plan Card",
                icon = Icons.Default.LocalHospital,
                backgroundColor = HealthGrey,
                textColor = TextBlack
            )

            WalletItemCard(
                text = "Vaccination Card",
                icon = Icons.Default.LocalHospital,
                backgroundColor = TextWhite,
                textColor = TextBlack
            )

            WalletItemCard(
                text = "LDWF Licenses",
                icon = Icons.Default.Shield, // Placeholder
                backgroundColor = LicenseGreen,
                textColor = TextWhite
            )

            WalletItemCard(
                text = "Share mDL with TSA",
                icon = Icons.Default.Shield, // Placeholder
                backgroundColor = TsaBlue,
                textColor = TextWhite
            )

            Spacer(modifier = Modifier.height(16.dp))

            // The Driver's License Main Card
            LicensePreviewCard()
        }
    }
}

@Composable
fun WalletItemCard(
    text: String,
    icon: ImageVector,
    backgroundColor: Color,
    textColor: Color
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .clickable { /* TODO: Open Detail */ },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = textColor,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = text,
                color = textColor,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp
            )
        }
    }
}

@Composable
fun LicensePreviewCard() {
    // This represents the large license card at the bottom of the scroll
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(240.dp) // Approximate height from screenshot
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White) // The license background
            .clickable { /* TODO: Expand License */ }
    ) {
        // In a real app, this would be the actual Render of the license
        // Here we simulate the "Blurred/Obscured" view with the Overlay
        
        // Placeholder for the actual License Image
        // Image(painter = painterResource(id = R.drawable.license_bg), ...)
        
        // The "Tap to View" Overlay
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(LicenseOverlayBlue.copy(alpha = 0.8f)), // Semi-transparent blue
            contentAlignment = Alignment.Center
        ) {
             Column(horizontalAlignment = Alignment.CenterHorizontally) {
                 Text(
                     text = "Tap to View",
                     color = TextWhite,
                     fontWeight = FontWeight.Bold,
                     fontSize = 20.sp
                 )
                 // This is where the face and text would faintly show through if we weren't just cloning the UI structure
             }
        }
        
        // Hardcoded Text Overlay to match screenshot structure (Mock Data)
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(16.dp)
        ) {
             // We can't see the text clearly in the "Tap to View" mode in the screenshot provided (edited-image.jpg), 
             // but usually, it shows the name or ID briefly.
             // Leaving empty to match the "Overlay" state.
        }
    }
}

@Composable
fun androidx.compose.foundation.layout.RowScope.BottomNavItem(
    label: String,
    icon: ImageVector,
    selected: Boolean
) {
    NavigationBarItem(
        selected = selected,
        onClick = { /* TODO */ },
        icon = { Icon(icon, contentDescription = label) },
        label = { Text(label) },
        colors = NavigationBarItemDefaults.colors(
            selectedIconColor = TextWhite,
            unselectedIconColor = TextWhite.copy(alpha = 0.6f),
            selectedTextColor = TextWhite,
            unselectedTextColor = TextWhite.copy(alpha = 0.6f),
            indicatorColor = StateBlue // Hide the pill indicator or make it blend
        )
    )
}
