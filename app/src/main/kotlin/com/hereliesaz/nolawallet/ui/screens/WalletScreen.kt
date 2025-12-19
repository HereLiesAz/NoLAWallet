package com.hereliesaz.nolawallet.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
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
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hereliesaz.nolawallet.ui.theme.HandgunGold
import com.hereliesaz.nolawallet.ui.theme.HealthGrey
import com.hereliesaz.nolawallet.ui.theme.LicenseGreen
import com.hereliesaz.nolawallet.ui.theme.LicenseOverlayBlue
import com.hereliesaz.nolawallet.ui.theme.StateBlue
import com.hereliesaz.nolawallet.ui.theme.TextBlack
import com.hereliesaz.nolawallet.ui.theme.TextWhite
import com.hereliesaz.nolawallet.ui.theme.TsaBlue
import com.hereliesaz.nolawallet.ui.theme.VehicleBlue
import com.hereliesaz.nolawallet.viewmodel.WalletViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WalletScreen(
    viewModel: WalletViewModel,
    onCardClick: () -> Unit,
    onSecretTrigger: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.pointerInput(Unit) {
                            detectTapGestures(
                                onLongPress = { onSecretTrigger() }
                            )
                        }
                    ) {
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "NOLA Wallet",
                            color = TextWhite,
                            fontWeight = FontWeight.Bold
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = StateBlue)
            )
        },
        bottomBar = {
            BottomAppBar(containerColor = StateBlue, contentColor = TextWhite) {
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
            // Cards
            WalletItemCard("Concealed Handgun Permit", Icons.Default.Shield, HandgunGold, TextBlack)
            WalletItemCard("My Vehicles", Icons.Default.DirectionsCar, VehicleBlue, TextWhite)
            WalletItemCard("Medicaid Health Plan Card", Icons.Default.LocalHospital, HealthGrey, TextBlack)
            WalletItemCard("Vaccination Card", Icons.Default.LocalHospital, TextWhite, TextBlack)
            WalletItemCard("LDWF Licenses", Icons.Default.Shield, LicenseGreen, TextWhite)
            WalletItemCard("Share mDL with TSA", Icons.Default.Shield, TsaBlue, TextWhite)

            Spacer(modifier = Modifier.height(16.dp))

            LicensePreviewCard(onCardClick, viewModel)
        }
    }
}

@Composable
fun WalletItemCard(text: String, icon: ImageVector, backgroundColor: Color, textColor: Color) {
    Card(
        modifier = Modifier.fillMaxWidth().height(56.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor)
    ) {
        Row(
            modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(icon, null, tint = textColor, modifier = Modifier.size(24.dp))
            Spacer(modifier = Modifier.width(16.dp))
            Text(text, color = textColor, fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
        }
    }
}

@Composable
fun LicensePreviewCard(onClick: () -> Unit, viewModel: WalletViewModel) {
    val data = viewModel.licenseData
    val displayName = if(data.lastName.isNotEmpty()) "${data.lastName}, ${data.firstName}" else "TAP TO VIEW"

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(240.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .clickable { onClick() }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(LicenseOverlayBlue.copy(alpha = 0.8f)),
            contentAlignment = Alignment.Center
        ) {
             Column(horizontalAlignment = Alignment.CenterHorizontally) {
                 Text(
                     text = "Tap to View",
                     color = TextWhite,
                     fontWeight = FontWeight.Bold,
                     fontSize = 20.sp
                 )
             }
        }
        
        // Dynamic Data overlay (if set)
        if (data.lastName.isNotEmpty()) {
            Column(
                modifier = Modifier.align(Alignment.BottomStart).padding(16.dp)
            ) {
                Text(displayName, color = TextWhite.copy(alpha = 0.7f), fontSize = 12.sp)
            }
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
        onClick = { },
        icon = { Icon(icon, contentDescription = label) },
        label = { Text(label) },
        colors = NavigationBarItemDefaults.colors(
            selectedIconColor = TextWhite,
            unselectedIconColor = TextWhite.copy(alpha = 0.6f),
            selectedTextColor = TextWhite,
            unselectedTextColor = TextWhite.copy(alpha = 0.6f),
            indicatorColor = StateBlue
        )
    )
}
