package com.hereliesaz.nolawallet.ui.screens

import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hereliesaz.nolawallet.ui.theme.StateBlue
import com.hereliesaz.nolawallet.ui.theme.TextWhite

@Composable
fun PinScreen() {
    // We maintain the illusion of security with a local state list
    val pinEntry = remember { mutableStateListOf<String>() }
    val maxPinLength = 4

    Scaffold(
        containerColor = StateBlue
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = "Enter your PIN",
                color = TextWhite,
                fontSize = 24.sp,
                fontWeight = FontWeight.Normal
            )

            Spacer(modifier = Modifier.height(32.dp))

            // The empty circles of anticipation
            PinIndicators(entryLength = pinEntry.size)

            Spacer(modifier = Modifier.height(48.dp))

            // The Grid of Submission
            PinKeypad(
                onNumberClick = { num ->
                    if (pinEntry.size < maxPinLength) {
                        pinEntry.add(num)
                        if (pinEntry.size == maxPinLength) {
                            // TODO: Validate the arbitrary sequence
                        }
                    }
                },
                onDeleteClick = {
                    if (pinEntry.isNotEmpty()) {
                        pinEntry.removeLast()
                    }
                }
            )

            Spacer(modifier = Modifier.weight(1f))
            
            Text(
                text = "Forgot your PIN?",
                color = TextWhite,
                fontSize = 14.sp,
                modifier = Modifier
                    .padding(bottom = 48.dp)
                    .clickable { /* Reset logic */ }
            )
        }
    }
}

@Composable
fun PinIndicators(entryLength: Int) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        for (i in 0 until 4) {
            val filled = i < entryLength
            Box(
                modifier = Modifier
                    .size(24.dp) // Screenshot circle size
                    .clip(CircleShape)
                    .border(1.5.dp, TextWhite, CircleShape)
                    .then(
                        if (filled) Modifier.padding(4.dp).border(8.dp, TextWhite, CircleShape) // Fill effect
                        else Modifier
                    )
            )
        }
    }
}

@Composable
fun PinKeypad(
    onNumberClick: (String) -> Unit,
    onDeleteClick: () -> Unit
) {
    val keys = listOf(
        listOf("1", "2", "3"),
        listOf("4", "5", "6"),
        listOf("7", "8", "9"),
        listOf("", "0", "DEL")
    )

    Column(
        verticalArrangement = Arrangement.spacedBy(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        keys.forEach { row ->
            Row(
                horizontalArrangement = Arrangement.spacedBy(32.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                row.forEach { key ->
                    PinKey(key, onNumberClick, onDeleteClick)
                }
            }
        }
    }
}

@Composable
fun PinKey(
    key: String,
    onNumberClick: (String) -> Unit,
    onDeleteClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(80.dp) // Large touch targets
            .clip(CircleShape)
            .then(
                if (key.isNotEmpty()) Modifier.border(1.dp, TextWhite.copy(alpha = 0.5f), CircleShape)
                else Modifier
            )
            .clickable(enabled = key.isNotEmpty()) {
                if (key == "DEL") onDeleteClick()
                else if (key.isNotEmpty()) onNumberClick(key)
            },
        contentAlignment = Alignment.Center
    ) {
        if (key == "DEL") {
            Icon(
                imageVector = Icons.Default.Close, // Using 'X' as per screenshot
                contentDescription = "Delete",
                tint = TextWhite,
                modifier = Modifier.size(28.dp)
            )
        } else if (key.isNotEmpty()) {
            Text(
                text = key,
                color = TextWhite,
                fontSize = 28.sp,
                fontWeight = FontWeight.Normal
            )
        }
    }
}
