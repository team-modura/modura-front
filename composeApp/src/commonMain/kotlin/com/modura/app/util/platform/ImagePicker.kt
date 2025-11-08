package com.modura.app.util.platform

import androidx.compose.runtime.Composable

@Composable
expect fun rememberImagePicker(
    onResult: (List<String>) -> Unit
): ImagePicker

interface ImagePicker {
    fun pickImages()
}