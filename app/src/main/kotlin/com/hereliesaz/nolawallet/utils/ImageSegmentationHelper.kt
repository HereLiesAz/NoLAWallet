package com.hereliesaz.nolawallet.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color as AndroidColor
import android.net.Uri
import androidx.compose.ui.graphics.toArgb
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.segmentation.subject.SubjectSegmentation
import com.google.mlkit.vision.segmentation.subject.SubjectSegmenterOptions
import com.hereliesaz.nolawallet.ui.theme.LicensePhotoBackgroundBlue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.nio.ByteBuffer

object ImageSegmentationHelper {

    suspend fun processAndSaveImage(context: Context, inputUri: Uri): String? {
        return withContext(Dispatchers.IO) {
            try {
                // 1. Load the Input Bitmap, ensuring it's mutable so we can modify pixels
                val inputStream = context.contentResolver.openInputStream(inputUri)
                val originalBitmap = BitmapFactory.decodeStream(inputStream)
                    .copy(Bitmap.Config.ARGB_8888, true)
                inputStream?.close()

                // 2. Configure ML Kit Segmenter
                val options = SubjectSegmenterOptions.Builder()
                    .enableForegroundBitmap() // We want the mask result
                    .build()
                val segmenter = SubjectSegmentation.getClient(options)
                val inputImage = InputImage.fromBitmap(originalBitmap, 0)

                // 3. Run inference (async)
                val result = segmenter.process(inputImage).await()
                val foregroundMask = result.foregroundBitmap ?: return@withContext null

                // 4. Compose the final image
                // We have the original image and the mask. We need to merge them.
                val finalBitmap = applyBlueBackground(originalBitmap, foregroundMask)

                // 5. Save final processed image to internal storage
                val fileName = "processed_license_photo_${System.currentTimeMillis()}.jpg"
                val file = File(context.filesDir, fileName)
                val outputStream = FileOutputStream(file)
                // Use JPEG with high quality
                finalBitmap.compress(Bitmap.CompressFormat.JPEG, 95, outputStream)
                outputStream.close()
                
                // Clean up memory
                originalBitmap.recycle()
                foregroundMask.recycle()
                finalBitmap.recycle()

                file.absolutePath
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }

    /**
     * Combines original image with mask, replacing background with solid blue.
     * Using pixel arrays for performance instead of getPixel/setPixel.
     */
    private fun applyBlueBackground(original: Bitmap, mask: Bitmap): Bitmap {
        val width = original.width
        val height = original.height
        val resultBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val bgColorInt = LicensePhotoBackgroundBlue.toArgb()

        val originalPixels = IntArray(width * height)
        val maskPixels = IntArray(width * height)
        val resultPixels = IntArray(width * height)

        original.getPixels(originalPixels, 0, width, 0, 0, width, height)
        mask.getPixels(maskPixels, 0, width, 0, 0, width, height)

        for (i in originalPixels.indices) {
            // The mask bitmap is black (bg) and white (fg). We check the red channel.
            // If it's high (white), it's foreground.
            val maskVal = AndroidColor.red(maskPixels[i])
            
            if (maskVal > 128) {
                // Foreground: keep original pixel
                resultPixels[i] = originalPixels[i]
            } else {
                // Background: replace with solid blue
                resultPixels[i] = bgColorInt
            }
        }

        resultBitmap.setPixels(resultPixels, 0, width, 0, 0, width, height)
        return resultBitmap
    }
}
