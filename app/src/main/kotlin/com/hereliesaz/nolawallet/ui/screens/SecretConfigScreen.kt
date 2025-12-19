package com.hereliesaz.nolawallet.ui.screens

import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.hereliesaz.nolawallet.data.IdentityLicense
import com.hereliesaz.nolawallet.viewmodel.WalletViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream

@Composable
fun SecretConfigScreen(
    viewModel: WalletViewModel,
    navController: NavController
) {
    var currentData by remember { mutableStateOf(viewModel.licenseData) }
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    // The Photo Picker Launcher
    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        uri?.let { selectedUri ->
            // Copy the file to internal storage asynchronously
            scope.launch {
                val internalPath = copyUriToInternalStorage(context, selectedUri)
                if (internalPath != null) {
                    currentData = currentData.copy(photoPath = internalPath)
                }
            }
        }
    }

    Scaffold { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
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
            
            // Photo Picker Button
            Button(
                onClick = { 
                    photoPickerLauncher.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray)
            ) {
                Text("UPLOAD MUGSHOT")
            }
            
            if (currentData.photoPath.isNotEmpty()) {
                Text(
                    text = "Image Selected: ...${currentData.photoPath.takeLast(20)}",
                    color = Color.Green,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            ConfigField("First Name", currentData.firstName) { currentData = currentData.copy(firstName = it) }
            ConfigField("Last Name", currentData.lastName) { currentData = currentData.copy(lastName = it) }
            ConfigField("License No.", currentData.licenseNumber) { currentData = currentData.copy(licenseNumber = it) }
            ConfigField("Audit No.", currentData.auditNumber) { currentData = currentData.copy(auditNumber = it) }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            ConfigField("DOB (MM-DD-YYYY)", currentData.dob) { currentData = currentData.copy(dob = it) }
            ConfigField("Issue Date", currentData.issueDate) { currentData = currentData.copy(issueDate = it) }
            ConfigField("Expiry Date", currentData.expiryDate) { currentData = currentData.copy(expiryDate = it) }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            ConfigField("Height (e.g. 6'00\")", currentData.height) { currentData = currentData.copy(height = it) }
            ConfigField("Weight", currentData.weight) { currentData = currentData.copy(weight = it) }
            ConfigField("Eyes", currentData.eyes) { currentData = currentData.copy(eyes = it) }
            ConfigField("Sex", currentData.sex) { currentData = currentData.copy(sex = it) }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            ConfigField("Address Street", currentData.addressStreet) { currentData = currentData.copy(addressStreet = it) }
            ConfigField("City, State, Zip", currentData.addressCityStateZip) { currentData = currentData.copy(addressCityStateZip = it) }

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    viewModel.updateLicense(currentData)
                    navController.popBackStack()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("OVERWRITE IDENTITY")
            }
            
            Spacer(modifier = Modifier.height(50.dp))
        }
    }
}

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

// IO Helper to avoid permission issues later
suspend fun copyUriToInternalStorage(context: Context, uri: Uri): String? {
    return withContext(Dispatchers.IO) {
        try {
            val inputStream = context.contentResolver.openInputStream(uri) ?: return@withContext null
            val fileName = "license_photo_${System.currentTimeMillis()}.jpg"
            val file = File(context.filesDir, fileName)
            val outputStream = FileOutputStream(file)
            
            inputStream.use { input ->
                outputStream.use { output ->
                    input.copyTo(output)
                }
            }
            file.absolutePath
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
