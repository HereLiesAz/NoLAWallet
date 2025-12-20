package com.hereliesaz.nolawallet.ui.screens

import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.width
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.FileProvider
import androidx.navigation.NavController
import com.hereliesaz.nolawallet.ui.theme.StateBlue
import com.hereliesaz.nolawallet.util.ImageSegmentationHelper
import com.hereliesaz.nolawallet.viewmodel.WalletViewModel
import kotlinx.coroutines.launch
import java.io.File

@Composable
fun SecretConfigScreen(
    viewModel: WalletViewModel,
    navController: NavController
) {
    var currentData by remember { mutableStateOf(viewModel.licenseData) }
    var isProcessingImage by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    // Create a temporary file to store the full-size camera image
    val tempImageUri by remember {
        mutableStateOf(createTempPictureUri(context))
    }

    // The Camera Launcher
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (success && tempImageUri != null) {
            isProcessingImage = true
            scope.launch {
                // Run the ML Kit processing helper
                val processedPath = ImageSegmentationHelper.processAndSaveImage(context, tempImageUri!!)
                if (processedPath != null) {
                    currentData = currentData.copy(photoPath = processedPath)
                }
                isProcessingImage = false
            }
        }
    }

    Scaffold { padding ->
        Box(modifier = Modifier.fillMaxSize().padding(padding)) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Text(
                    text = "SYSTEM CONFIGURATION",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Red
                )
                Text("Modify Identity Parameters", color = Color.Gray)

                Spacer(modifier = Modifier.height(24.dp))
                
                // Camera Button
                Button(
                    onClick = { cameraLauncher.launch(tempImageUri) },
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = StateBlue),
                    enabled = !isProcessingImage
                ) {
                    Icon(Icons.Default.CameraAlt, null)
                    Spacer(Modifier.width(8.dp))
                    Text("TAKE SELFIE (AUTO-BG REMOVE)")
                }
                
                if (currentData.photoPath.isNotEmpty() && !isProcessingImage) {
                    Text(
                        text = "Processed Image Ready",
                        color = Color(0xFF4CAF50), // Green
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Fields (same as before)
                ConfigField("First Name", currentData.firstName) { currentData = currentData.copy(firstName = it) }
                ConfigField("Last Name", currentData.lastName) { currentData = currentData.copy(lastName = it) }
                // ... rest of fields ... (omitted for brevity, they are identical to previous step)
                ConfigField("License No.", currentData.licenseNumber) { currentData = currentData.copy(licenseNumber = it) }
                ConfigField("Audit No.", currentData.auditNumber) { currentData = currentData.copy(auditNumber = it) }
                ConfigField("DOB (MM-DD-YYYY)", currentData.dob) { currentData = currentData.copy(dob = it) }
                ConfigField("Issue Date", currentData.issueDate) { currentData = currentData.copy(issueDate = it) }
                ConfigField("Expiry Date", currentData.expiryDate) { currentData = currentData.copy(expiryDate = it) }
                ConfigField("Height (e.g. 6'00\")", currentData.height) { currentData = currentData.copy(height = it) }
                ConfigField("Weight", currentData.weight) { currentData = currentData.copy(weight = it) }
                ConfigField("Eyes", currentData.eyes) { currentData = currentData.copy(eyes = it) }
                ConfigField("Sex", currentData.sex) { currentData = currentData.copy(sex = it) }
                ConfigField("Address Street", currentData.addressStreet) { currentData = currentData.copy(addressStreet = it) }
                ConfigField("City, State, Zip", currentData.addressCityStateZip) { currentData = currentData.copy(addressCityStateZip = it) }

                Spacer(modifier = Modifier.height(32.dp))

                Button(
                    onClick = {
                        viewModel.updateLicense(currentData)
                        navController.popBackStack()
                    },
                    modifier = Modifier.fillMaxWidth().height(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                    enabled = !isProcessingImage
                ) {
                    Text("OVERWRITE IDENTITY", fontWeight = FontWeight.Bold)
                }
                
                Spacer(modifier = Modifier.height(50.dp))
            }

            // Loading Overlay
            if (isProcessingImage) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.7f)),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        CircularProgressIndicator(color = Color.White)
                        Spacer(Modifier.height(16.dp))
                        Text("Processing Neural Segmentation...", color = Color.White)
                    }
                }
            }
        }
    }
}

// Helper to create a temp URI for the camera to write to
private fun createTempPictureUri(context: Context): Uri {
    val tempFile = File.createTempFile("camera_capture", ".jpg", context.cacheDir).apply {
        createNewFile()
        deleteOnExit()
    }
    // Note: You need to set up a FileProvider in AndroidManifest.xml for this to work correctly on Android 7+
    // Assuming the standard authority string here.
    return FileProvider.getUriForFile(context, "${context.packageName}.provider", tempFile)
}

// Helper component re-added for completeness within this file scope
@Composable
fun ConfigField(label: String, value: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        singleLine = true
    )
}
