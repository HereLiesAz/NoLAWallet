package com.hereliesaz.nolawallet.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hereliesaz.nolawallet.ui.theme.DividerGrey
import com.hereliesaz.nolawallet.ui.theme.LicenseGreen
import com.hereliesaz.nolawallet.ui.theme.LightGrey
import com.hereliesaz.nolawallet.ui.theme.StateBlue
import com.hereliesaz.nolawallet.ui.theme.TextBlack
import com.hereliesaz.nolawallet.ui.theme.TextWhite
import com.hereliesaz.nolawallet.viewmodel.WalletViewModel

@Composable
fun ShareLicenseScreen(
    viewModel: WalletViewModel,
    onBackClick: () -> Unit
) {
    var isLicenseVisible by remember { mutableStateOf(true) }
    val data = viewModel.licenseData
    val fullName = if (data.firstName.isEmpty()) "JEFFREY AZRIENOCH SMITH-LUEDKE" else "${data.firstName} ${data.lastName}"

    Scaffold(
        topBar = {
            Column(Modifier.background(StateBlue)) {
                // Header Title with Back
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
                ) {
                    IconButton(
                        onClick = onBackClick,
                        modifier = Modifier.align(Alignment.CenterStart)
                    ) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = TextWhite)
                    }
                    Text(
                        text = "Share License",
                        color = TextWhite,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                
                // Tabs
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .background(LightGrey, RoundedCornerShape(20.dp))
                        .padding(4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    TabButton("Verify You", true)
                    TabButton("Remote Verify", false)
                    TabButton("Document You", false)
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color(0xFFF5F5F5))
                .padding(16.dp)
        ) {
            
            // QR Face Container
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(350.dp)
                    .background(Color.White)
            ) {
                Box(modifier = Modifier.fillMaxSize().padding(20.dp).background(Color.LightGray)) // Face Placeholder
                QrMarker(Alignment.TopStart)
                QrMarker(Alignment.TopEnd)
                QrMarker(Alignment.BottomStart)
                QrMarker(Alignment.BottomEnd, isSmall = true)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Status Cards
            Row(modifier = Modifier.fillMaxWidth()) {
                StatusCard("MY PORTRAIT", "Yes", Modifier.weight(1f))
                Spacer(modifier = Modifier.width(8.dp))
                StatusCard("LICENSE STATUS", "Valid", Modifier.weight(1f))
            }
            Spacer(modifier = Modifier.height(8.dp))
            StatusCard("AGE", "Over 25", Modifier.fillMaxWidth())

            Spacer(modifier = Modifier.height(16.dp))

            // Toggles
            ToggleRow("NAME", fullName, true)
            Spacer(modifier = Modifier.height(8.dp))
            ToggleRow("LICENSE NUMBER", if (data.licenseNumber.isEmpty()) "012033589" else data.licenseNumber, isLicenseVisible)
        }
    }
}

// Helpers reused
@Composable
fun TabButton(text: String, isSelected: Boolean) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(if (isSelected) Color.White else Color.Transparent)
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .border(if (isSelected) 1.dp else 0.dp, if (isSelected) TextBlack else Color.Transparent, RoundedCornerShape(16.dp))
    ) {
        Text(text, fontSize = 12.sp, fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal, color = TextBlack)
    }
}

@Composable
fun BoxScope.QrMarker(alignment: Alignment, isSmall: Boolean = false) {
    val size = if (isSmall) 30.dp else 60.dp
    val color = Color(0xFF263238)
    Box(
        modifier = Modifier
            .align(alignment)
            .size(size)
            .background(Color.White)
            .padding(if (isSmall) 4.dp else 8.dp)
            .border(if (isSmall) 4.dp else 8.dp, color)
            .padding(if (isSmall) 4.dp else 8.dp)
            .background(color)
    )
}

@Composable
fun StatusCard(title: String, value: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .background(Color.White)
            .border(1.dp, DividerGrey)
            .padding(horizontal = 12.dp, vertical = 8.dp)
    ) {
        Text(title, fontSize = 10.sp, color = Color.Gray)
        Spacer(modifier = Modifier.height(4.dp))
        Text(value, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = TextBlack)
    }
}

@Composable
fun ToggleRow(title: String, value: String, checked: Boolean) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .border(1.dp, DividerGrey)
            .padding(12.dp)
    ) {
        Text(title, fontSize = 10.sp, color = Color.Gray)
        Spacer(modifier = Modifier.height(4.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(value, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = TextBlack)
            Switch(
                checked = checked,
                onCheckedChange = { },
                colors = SwitchDefaults.colors(checkedThumbColor = Color.White, checkedTrackColor = LicenseGreen)
            )
        }
    }
}
