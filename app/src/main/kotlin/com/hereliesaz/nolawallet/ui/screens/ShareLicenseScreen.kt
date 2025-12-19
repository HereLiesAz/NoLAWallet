package com.hereliesaz.nolawallet.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.hereliesaz.nolawallet.ui.theme.StateBlue
import com.hereliesaz.nolawallet.ui.theme.TextBlack
import com.hereliesaz.nolawallet.ui.theme.TextWhite

@Composable
fun ShareLicenseScreen() {
    var isLicenseVisible by remember { mutableStateOf(true) }

    Scaffold(
        topBar = {
            Column(Modifier.background(StateBlue)) {
                // Header Title
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Share License",
                        color = TextWhite,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold
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
                .background(Color(0xFFF5F5F5)) // Light grey bg
                .padding(16.dp)
        ) {
            
            // 1. The QR Face Container
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(350.dp) // Square-ish area
                    .background(Color.White)
            ) {
                // In reality, this is a complex view with a camera feed or static image + overlay
                // We construct the "Corner Markers" of a QR code manually to simulate the look
                
                // The Image Placeholder (The Face)
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(20.dp) // Inset from markers
                        .background(Color.LightGray)
                ) {
                    // TODO: Insert Face Image Here
                }
                
                // The QR Corner Markers
                QrMarker(Alignment.TopStart)
                QrMarker(Alignment.TopEnd)
                QrMarker(Alignment.BottomStart)
                // Bottom Right is usually the alignment pattern or empty in this specific UI
                QrMarker(Alignment.BottomEnd, isSmall = true) 
                
                // The Dot Overlay Simulation (Abstracted)
                // We aren't drawing thousands of dots, but we acknowledge their existence.
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 2. Status Cards
            Row(modifier = Modifier.fillMaxWidth()) {
                StatusCard(
                    title = "MY PORTRAIT",
                    value = "Yes",
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                StatusCard(
                    title = "LICENSE STATUS",
                    value = "Valid",
                    modifier = Modifier.weight(1f)
                )
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            StatusCard(title = "AGE", value = "Over 25", modifier = Modifier.fillMaxWidth())

            Spacer(modifier = Modifier.height(16.dp))

            // 3. Toggles
            ToggleRow(title = "NAME", value = "Jeffrey Azrienoch Smith-Luedke", checked = true)
            Spacer(modifier = Modifier.height(8.dp))
            ToggleRow(title = "LICENSE NUMBER", value = "012033589", checked = isLicenseVisible)
        }
    }
}

@Composable
fun TabButton(text: String, isSelected: Boolean) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(if (isSelected) Color.White else Color.Transparent)
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .border(
                width = if (isSelected) 1.dp else 0.dp, 
                color = if (isSelected) TextBlack else Color.Transparent, 
                shape = RoundedCornerShape(16.dp)
            )
    ) {
        Text(
            text = text,
            fontSize = 12.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
            color = TextBlack
        )
    }
}

@Composable
fun BoxScope.QrMarker(alignment: Alignment, isSmall: Boolean = false) {
    val size = if (isSmall) 30.dp else 60.dp
    val color = Color(0xFF263238) // Dark grey/blue for QR blocks

    Box(
        modifier = Modifier
            .align(alignment)
            .size(size)
            .background(Color.White) // White quiet zone
            .padding(if (isSmall) 4.dp else 8.dp) // Inner padding
            .border(if (isSmall) 4.dp else 8.dp, color) // Outer box
            .padding(if (isSmall) 4.dp else 8.dp) // Space between outer and inner
            .background(color) // Inner box
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
        Text(text = title, fontSize = 10.sp, color = Color.Gray)
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = value, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = TextBlack)
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
        Text(text = title, fontSize = 10.sp, color = Color.Gray)
        Spacer(modifier = Modifier.height(4.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = value,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = TextBlack
            )
            Switch(
                checked = checked,
                onCheckedChange = { },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color.White,
                    checkedTrackColor = LicenseGreen
                )
            )
        }
    }
}
