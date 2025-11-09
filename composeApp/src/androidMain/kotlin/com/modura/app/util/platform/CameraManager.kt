package com.modura.app.util.platform

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.icu.text.SimpleDateFormat
import android.media.ExifInterface
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.autofill.ContentDataType
import androidx.compose.ui.graphics.ImageBitmap
import android.graphics.Matrix
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.FileProvider
import java.io.File
import java.util.Date
import java.util.Objects
import kotlin.text.format


@Composable
actual fun rememberCameraManager(onResult: (ImageBitmap?) -> Unit): () -> Unit {

    val context = LocalContext.current
    var tempUri: Uri? = null

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = { success ->
            if (success) {
                tempUri?.let { uri ->
                    val rotatedBitmap = rotateBitmapFromUriIfNeeded(context, uri)
                    onResult(rotatedBitmap?.asImageBitmap())
                }
            } else {
                onResult(null)
            }
        }
    )

    return remember {
        {
            val tempFile = createTempImageFile(context)
            tempUri = FileProvider.getUriForFile(
                Objects.requireNonNull(context),
                "${context.packageName}.provider",
                tempFile
            )
            launcher.launch(tempUri)
        }
    }
}

private fun createTempImageFile(context: Context): File {
    val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
    val storageDir: File? = context.getExternalFilesDir(null)
    return File.createTempFile(
        "JPEG_${timeStamp}_",
        ".jpg",
        storageDir
    )
}

private fun rotateBitmapFromUriIfNeeded(context: Context, uri: Uri): Bitmap? {

    val bitmap = context.contentResolver.openInputStream(uri)?.use { inputStream ->
        BitmapFactory.decodeStream(inputStream)
    } ?: return null

    val exifInterface = context.contentResolver.openInputStream(uri)?.use { inputStream ->
        ExifInterface(inputStream)
    } ?: return bitmap

    val orientation = exifInterface.getAttributeInt(
        ExifInterface.TAG_ORIENTATION,
        ExifInterface.ORIENTATION_NORMAL
    )

    val matrix = Matrix()
    when (orientation) {
        ExifInterface.ORIENTATION_ROTATE_90 -> matrix.postRotate(90f)
        ExifInterface.ORIENTATION_ROTATE_180 -> matrix.postRotate(180f)
        ExifInterface.ORIENTATION_ROTATE_270 -> matrix.postRotate(270f)
    }

    return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
}