package com.modura.app.util.platform

import android.content.Context
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

actual fun saveBitmapToFile(context: Any,bitmap: ImageBitmap): String {
    val androidContext = context as Context
    val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
    val tempFile = File(context.cacheDir, "STILLCUT_${timeStamp}.jpg")

    FileOutputStream(tempFile).use { out ->
        bitmap.asAndroidBitmap().compress(
            android.graphics.Bitmap.CompressFormat.JPEG,
            85, // 85% 품질로 압축
            out
        )
    }
    return tempFile.absolutePath
}