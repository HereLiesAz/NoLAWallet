package com.hereliesaz.nolawallet.ui.screens

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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.hereliesaz.nolawallet.data.IdentityLicense
import com.hereliesaz.nolawallet.viewmodel.WalletViewModel

@Composable
fun SecretConfigScreen(
    viewModel: WalletViewModel,
    navController: NavController
) {
    // Load existing data into local state for editing
    var currentData by remember { mutableStateOf(viewModel.licenseData) }

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
