package com.modura.app.util.platform

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import io.ktor.client.HttpClient
import io.ktor.client.engine.darwin.Darwin
import io.ktor.client.request.get
import io.ktor.client.statement.readBytes
import org.jetbrains.skia.Image

@Composable
actual fun rememberImageBitmapFromUrl(
    url: String,
    onSuccess: (ImageBitmap) -> Unit,
    onFailure: (String) -> Unit
) {
    val httpClient = remember {HttpClient(Darwin) {
        }
    }
    LaunchedEffect(url) {
        try {
            val imageBytes = httpClient.get(url).readBytes()

            val imageBitmap = Image.makeFromEncoded(imageBytes).toComposeImageBitmap()

            onSuccess(imageBitmap)

        } catch (e: Exception) {
            e.printStackTrace()
            onFailure("이미지 로딩 실패: ${e.message}")
        }
    }
}