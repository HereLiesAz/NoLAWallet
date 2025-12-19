package com.hereliesaz.nolawallet.ui.screens

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hereliesaz.nolawallet.ui.theme.DividerGrey
import com.hereliesaz.nolawallet.ui.theme.LicenseGreen
import com.hereliesaz.nolawallet.ui.theme.LightGrey
import com.hereliesaz.nolawallet.ui.theme.StateBlue
import com.hereliesaz.nolawallet.ui.theme.TextBlack
import com.hereliesaz.nolawallet.ui.theme.TextWhite
import com.hereliesaz.nolawallet.viewmodel.WalletViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LicenseDetailScreen(
    viewModel: WalletViewModel,
    onShareClick: () -> Unit,
    onBackClick: () -> Unit
) {
    val data = viewModel.licenseData
    val fullName = if (data.firstName.isEmpty()) "JEFFREY AZRIENOCH SMITH-LUEDKE" else "${data.firstName} ${data.lastName}"
    val licenseNo = if (data.licenseNumber.isEmpty()) "012033589" else data.licenseNumber

    // Bitmap Loader
    var licensePhoto by remember { mutableStateOf<Bitmap?>(null) }
    
    LaunchedEffect(data.photoPath) {
        if (data.photoPath.isNotEmpty()) {
            withContext(Dispatchers.IO) {
                try {
                    val file = File(data.photoPath)
                    if (file.exists()) {
                        licensePhoto = BitmapFactory.decodeFile(file.absolutePath)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("License Details", color = TextWhite) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = TextWhite)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = StateBlue)
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .background(Color.White)
        ) {
            // Visual Area
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(450.dp) // Maintain aspect ratio of a real license usually
                    .background(LightGrey),
                contentAlignment = Alignment.Center
            ) {
                if (licensePhoto != null) {
                    Image(
                        bitmap = licensePhoto!!.asImageBitmap(),
                        contentDescription = "License Photo",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                    
                    // Optional: Add the Blue overlay from the real app if desired, 
                    // but usually the detail screen shows the clear image.
                } else {
                    Text("[No Photo Configured]", color = Color.Gray, textAlign = TextAlign.Center)
                }
            }

            // Status Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFE8F5E9))
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Default.CheckCircle, "Valid", tint = LicenseGreen, modifier = Modifier.size(24.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text("VALID", fontWeight = FontWeight.Bold, color = TextBlack, fontSize = 14.sp)
                    Text("Last Updated: Just now", color = Color.Gray, fontSize = 11.sp)
                }
            }

            // Name and Age
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(fullName, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    Text(licenseNo, fontSize = 20.sp, fontWeight = FontWeight.Normal)
                }
                
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(LicenseGreen),
                    contentAlignment = Alignment.Center
                ) {
                    Text("25+", color = TextWhite, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }
            }

            // Barcode
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .padding(horizontal = 16.dp)
                    .background(Color.Black),
                contentAlignment = Alignment.Center
            ) {
                Row(modifier = Modifier.fillMaxSize()) {
                    repeat(60) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxSize()
                                .background(if (it % 2 == 0) Color.White else Color.Black)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Actions
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                Button(
                    onClick = { },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = StateBlue),
                    border = androidx.compose.foundation.BorderStroke(1.dp, StateBlue),
                    shape = RoundedCornerShape(24.dp)
                ) {
                    Text("Purchase Duplicate")
                }
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Button(
                    onClick = onShareClick,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = StateBlue),
                    shape = RoundedCornerShape(24.dp)
                ) {
                    Text("Share")
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Data Fields
            DataFieldItem("LICENSE / ID NUMBER", licenseNo)
            Row {
                DataFieldItem("AUDIT", data.auditNumber, Modifier.weight(1f))
                DataFieldItem("DATE OF BIRTH", data.dob, Modifier.weight(1f))
            }
            Row {
                DataFieldItem("ISSUE DATE", data.issueDate, Modifier.weight(1f))
                DataFieldItem("EXPIRATION DATE", data.expiryDate, Modifier.weight(1f))
            }
            DataFieldItem("RESTRICTIONS", data.restrictions)
            DataFieldItem("ENDORSEMENTS", data.endorsements)
            DataFieldItem("CLASS", data.classType)
            
            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

@Composable
fun DataFieldItem(label: String, value: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .border(1.dp, DividerGrey, RoundedCornerShape(4.dp))
            .padding(12.dp)
    ) {
        Text(label, fontSize = 11.sp, color = Color.Gray)
        Spacer(modifier = Modifier.height(4.dp))
        Text(if (value.isEmpty()) "--" else value, fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = TextBlack)
    }
}
