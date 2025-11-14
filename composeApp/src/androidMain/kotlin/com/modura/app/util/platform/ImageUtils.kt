package com.modura.app.util.platform

import android.content.Context
import android.net.Uri
import java.io.File
import java.io.IOException

lateinit var appContext: Context

actual fun readBytesFromFilePath(uri: String): ByteArray {
    try {
        return File(uri).readBytes()
    } catch (e: Exception) {
        throw IOException("Failed to read file from path: $uri. Reason: ${e.message}", e)
    }
}

actual fun readFileAsBytes(uri: String): ByteArray {
    if (!::appContext.isInitialized) {
        throw IllegalStateException("Context has not been initialized. Please initialize it in your Application class.")
    }

    val contentResolver = appContext.contentResolver
    val androidUri = Uri.parse(uri)

    return contentResolver.openInputStream(androidUri)?.use { inputStream ->
        inputStream.readBytes()
    } ?: throw _root_ide_package_.kotlinx.io.IOException("Failed to open input stream for URI: $uri")
}
