package com.modura.app.util.platform

import android.graphics.drawable.BitmapDrawable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import coil3.ImageLoader
import coil3.asDrawable
import coil3.compose.LocalPlatformContext
import coil3.request.ImageRequest
import coil3.request.SuccessResult


@Composable
actual fun rememberImageBitmapFromUrl(
    url: String,
    onSuccess: (ImageBitmap) -> Unit,
    onFailure: (String) -> Unit
) {
    val context = LocalPlatformContext.current
    val imageLoader = remember { ImageLoader(context) }

    // url이 바뀔 때마다 이미지 로딩을 다시 시도
    LaunchedEffect(url) {
        val request = ImageRequest.Builder(context)
            .data(url)
            .build()

        val result = imageLoader.execute(request)

        if (result is SuccessResult) {
            val bitmap = (result.image.asDrawable(context.resources) as? BitmapDrawable)?.bitmap
            if (bitmap != null) {
                onSuccess(bitmap.asImageBitmap())
            } else {
                onFailure("Bitmap으로 변환할 수 없습니다.")
            }
        } else {
            onFailure("이미지 로딩에 실패했습니다.")
        }
    }
}