package com.hereliesaz.nolawallet.ui.screens

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
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
import androidx.compose.runtime.DisposableEffect
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hereliesaz.nolawallet.ui.theme.DividerGrey
import com.hereliesaz.nolawallet.ui.theme.LicenseGreen
import com.hereliesaz.nolawallet.ui.theme.LightGrey
import com.hereliesaz.nolawallet.ui.theme.StateBlue
import com.hereliesaz.nolawallet.ui.theme.TextBlack
import com.hereliesaz.nolawallet.ui.theme.TextWhite
import com.hereliesaz.nolawallet.util.LicenseLayerMapper
import com.hereliesaz.nolawallet.viewmodel.WalletViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.InputStream
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LicenseDetailScreen(
    viewModel: WalletViewModel,
    onShareClick: () -> Unit,
    onBackClick: () -> Unit
) {
    val data = viewModel.licenseData
    val context = LocalContext.current

    // ---------------------------------------------------------
    // SENSOR LOGIC (The Parallax Effect)
    // ---------------------------------------------------------
    var offsetX by remember { mutableStateOf(0f) }
    var offsetY by remember { mutableStateOf(0f) }

    DisposableEffect(Unit) {
        val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        
        val listener = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent?) {
                event?.let {
                    // Invert and scale the sensor data for the parallax shift
                    // We clamp it so the hologram doesn't fly off the card
                    offsetX = (it.values[0] * -20f).coerceIn(-40f, 40f)
                    offsetY = (it.values[1] * 20f).coerceIn(-40f, 40f)
                }
            }
            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
        }

        sensorManager.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_UI)
        onDispose {
            sensorManager.unregisterListener(listener)
        }
    }

    // ---------------------------------------------------------
    // ASSET LOADING
    // ---------------------------------------------------------
    var licensePhoto by remember { mutableStateOf<Bitmap?>(null) }
    var assetBitmaps by remember { mutableStateOf<Map<String, Bitmap>>(emptyMap()) }

    LaunchedEffect(data.photoPath) {
        if (data.photoPath.isNotEmpty()) {
            withContext(Dispatchers.IO) {
                try {
                    val file = File(data.photoPath)
                    if (file.exists()) {
                        licensePhoto = BitmapFactory.decodeFile(file.absolutePath)
                    }
                } catch (e: Exception) { e.printStackTrace() }
            }
        }
    }

    LaunchedEffect(Unit) {
        withContext(Dispatchers.IO) {
            val loadedAssets = mutableMapOf<String, Bitmap>()
            LicenseLayerMapper.Layers.forEach { assetPath ->
                val bitmap = loadBitmapFromAssets(context, assetPath)
                if (bitmap != null) loadedAssets[assetPath] = bitmap
            }
            assetBitmaps = loadedAssets
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
            
            // ---------------------------------------------------------
            // THE RENDERER
            // ---------------------------------------------------------
            BoxWithConstraints(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1.585f)
                    .background(LightGrey)
            ) {
                val w = maxWidth
                val h = maxHeight

                // 1. Render Layers
                LicenseLayerMapper.Layers.forEach { assetPath ->
                    val bitmap = assetBitmaps[assetPath]
                    if (bitmap != null) {
                        val isHologram = assetPath.contains("UV", ignoreCase = true) || assetPath.contains("Micro", ignoreCase = true)
                        
                        // If it's a hologram, apply the sensor offset and transparency
                        val modifier = if (isHologram) {
                            Modifier
                                .fillMaxSize()
                                .offset { IntOffset(offsetX.roundToInt(), offsetY.roundToInt()) }
                        } else {
                            Modifier.fillMaxSize()
                        }
                        
                        // Holograms use 'Screen' or simple alpha blending logic here
                        val alpha = if (isHologram) 0.5f else 1.0f

                        Image(
                            bitmap = bitmap.asImageBitmap(),
                            contentDescription = null,
                            modifier = modifier,
                            contentScale = ContentScale.FillBounds,
                            alpha = alpha
                        )
                    }
                }

                // 2. Render User Photo
                if (licensePhoto != null) {
                    // Main
                    Image(
                        bitmap = licensePhoto!!.asImageBitmap(),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .offset(x = w * 0.05f, y = h * 0.22f)
                            .width(w * 0.26f)
                            .height(h * 0.50f)
                    )
                    // Ghost (Bottom Right)
                    Image(
                        bitmap = licensePhoto!!.asImageBitmap(),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        alpha = 0.3f,
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .offset(x = -(w * 0.05f), y = -(h * 0.05f))
                            .width(w * 0.15f)
                            .height(h * 0.25f)
                    )
                }

                // 3. Render Text
                val labelColor = Color(0xFF8B0000)
                val dataColor = Color.Black
                val headerRed = Color(0xFFD32F2F)

                @Composable
                fun LicenseText(text: String, xPct: Float, yPct: Float, color: Color = dataColor, scale: Float = 1.0f, isBold: Boolean = true) {
                    val responsiveSize = (w.value * 0.035f * scale).sp
                    Text(
                        text = text,
                        color = color,
                        fontSize = responsiveSize,
                        fontWeight = if (isBold) FontWeight.Bold else FontWeight.Normal,
                        modifier = Modifier.align(Alignment.TopStart).offset(x = w * xPct, y = h * yPct)
                    )
                }

                // Data Map
                LicenseText("4d. LIC NO", 0.35f, 0.13f, labelColor, 0.7f)
                LicenseText(data.licenseNumber, 0.35f, 0.16f, headerRed, 1.2f)
                LicenseText("4b. EXP", 0.65f, 0.13f, labelColor, 0.7f)
                LicenseText(data.expiryDate, 0.65f, 0.16f, headerRed, 1.2f)
                LicenseText("1. LAST", 0.35f, 0.26f, labelColor, 0.7f)
                LicenseText(data.lastName, 0.35f, 0.29f, dataColor, 1.1f)
                LicenseText("2. FIRST", 0.35f, 0.36f, labelColor, 0.7f)
                LicenseText(data.firstName, 0.35f, 0.39f, dataColor, 1.1f)
                LicenseText("8. ADDRESS", 0.35f, 0.48f, labelColor, 0.7f)
                LicenseText(data.addressStreet, 0.35f, 0.51f, dataColor, 0.9f)
                LicenseText(data.addressCityStateZip, 0.35f, 0.55f, dataColor, 0.9f)

                val rowY = 0.68f
                LicenseText("15. SEX", 0.35f, rowY, labelColor, 0.7f)
                LicenseText(data.sex, 0.35f, rowY + 0.03f, dataColor, 0.9f)
                LicenseText("16. HGT", 0.48f, rowY, labelColor, 0.7f)
                LicenseText(data.height, 0.48f, rowY + 0.03f, dataColor, 0.9f)
                LicenseText("17. WGT", 0.61f, rowY, labelColor, 0.7f)
                LicenseText(data.weight, 0.61f, rowY + 0.03f, dataColor, 0.9f)
                LicenseText("18. EYES", 0.74f, rowY, labelColor, 0.7f)
                LicenseText(data.eyes, 0.74f, rowY + 0.03f, dataColor, 0.9f)

                LicenseText("9. CLASS", 0.85f, 0.26f, labelColor, 0.7f)
                LicenseText(data.classType, 0.90f, 0.29f, dataColor, 1.1f)
                LicenseText("9a. END", 0.85f, 0.36f, labelColor, 0.7f)
                LicenseText(data.endorsements, 0.85f, 0.39f, dataColor, 0.9f)
                LicenseText("12. REST", 0.85f, 0.48f, labelColor, 0.7f)
                LicenseText(data.restrictions, 0.85f, 0.51f, dataColor, 0.9f)
                LicenseText("4a. ISS", 0.35f, 0.85f, labelColor, 0.7f)
                LicenseText(data.issueDate, 0.35f, 0.88f, dataColor, 0.9f)
                LicenseText("3. DOB", 0.55f, 0.85f, labelColor, 0.7f)
                LicenseText(data.dob, 0.55f, 0.88f, headerRed, 1.0f)
                LicenseText("5. DD", 0.75f, 0.85f, labelColor, 0.7f)
                LicenseText(data.auditNumber, 0.75f, 0.88f, dataColor, 0.9f)
            }

            // ---------------------------------------------------------
            // BOTTOM UI
            // ---------------------------------------------------------
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

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    val fullName = if (data.firstName.isEmpty()) "JEFFREY AZRIENOCH SMITH-LUEDKE" else "${data.firstName} ${data.lastName}"
                    Text(fullName, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    Text(if (data.licenseNumber.isEmpty()) "012033589" else data.licenseNumber, fontSize = 20.sp, fontWeight = FontWeight.Normal)
                }
                Box(
                    modifier = Modifier.size(36.dp).clip(CircleShape).background(LicenseGreen),
                    contentAlignment = Alignment.Center
                ) {
                    Text("25+", color = TextWhite, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .padding(horizontal = 16.dp)
                    .background(Color.Black),
                contentAlignment = Alignment.Center
            ) {
                // PDF417 Simulation
                Row(modifier = Modifier.fillMaxSize()) {
                    repeat(60) {
                        Box(modifier = Modifier.weight(1f).fillMaxSize().background(if (it % 2 == 0) Color.White else Color.Black))
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

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
            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

// Re-using the helper
private fun loadBitmapFromAssets(context: Context, path: String): Bitmap? {
    return try {
        val cleanPath = path.removePrefix("assets/")
        val inputStream: InputStream = context.assets.open(cleanPath)
        BitmapFactory.decodeStream(inputStream)
    } catch (e: Exception) { null }
}
