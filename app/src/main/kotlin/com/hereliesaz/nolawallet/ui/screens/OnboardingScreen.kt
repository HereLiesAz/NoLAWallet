package com.hereliesaz.nolawallet.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hereliesaz.nolawallet.R
import com.hereliesaz.nolawallet.ui.theme.GoldAccent
import com.hereliesaz.nolawallet.ui.theme.StateBlue
import com.hereliesaz.nolawallet.ui.theme.TextWhite

@Composable
fun OnboardingScreen() {
    Scaffold { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // 1. The Stock Photo Background
            // In a real app, this is the woman in the car.
            // We simulate it with a placeholder box.
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp) // Covers top half
                    .background(Color.Gray) // Placeholder for image
            ) {
                // Image(
                //    painter = painterResource(id = R.drawable.woman_in_car),
                //    contentDescription = "Happy Citizen",
                //    contentScale = ContentScale.Crop,
                //    modifier = Modifier.fillMaxSize()
                // )
            }

            // 2. The Gradient Overlay (Transition to Blue)
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                StateBlue.copy(alpha = 0.8f),
                                StateBlue
                            ),
                            startY = 300f
                        )
                    )
            )

            // 3. Content Layer
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom
            ) {
                // The Logo
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                        .background(StateBlue)
                        .border(2.dp, TextWhite, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    // Icon(painter = painterResource(id = R.drawable.ic_la_map), tint = GoldAccent)
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Welcome to LA Wallet",
                    color = TextWhite,
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Use LA Wallet as your legally-recognized Louisiana Digital Driver's License.",
                    color = TextWhite.copy(alpha = 0.9f),
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    lineHeight = 22.sp
                )

                Spacer(modifier = Modifier.height(32.dp))

                // Pagination Dots
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.padding(bottom = 32.dp)
                ) {
                    Box(Modifier.size(8.dp).clip(CircleShape).background(TextWhite))
                    Box(Modifier.size(8.dp).clip(CircleShape).background(TextWhite.copy(alpha = 0.4f)))
                    Box(Modifier.size(8.dp).clip(CircleShape).background(TextWhite.copy(alpha = 0.4f)))
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Buttons
                Button(
                    onClick = { /* Create Account */ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF2196F3) // Brighter blue button
                    ),
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Text("CREATE AN ACCOUNT", fontWeight = FontWeight.SemiBold)
                }

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedButton(
                    onClick = { /* Login */ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = TextWhite),
                    border = androidx.compose.foundation.BorderStroke(1.dp, TextWhite),
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Text("LOGIN TO AN EXISTING ACCOUNT", fontWeight = FontWeight.SemiBold)
                }

                Spacer(modifier = Modifier.height(32.dp))
                
                Text(
                    text = "2.0.149.32248",
                    color = TextWhite.copy(alpha = 0.5f),
                    fontSize = 10.sp
                )
            }
        }
    }
}
