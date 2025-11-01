package com.modura.app.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.height
import androidx.compose.ui.unit.width
import com.modura.app.domain.model.response.youtube.YoutubeModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import modura.composeapp.generated.resources.Res
import modura.composeapp.generated.resources.img_example
import org.jetbrains.compose.resources.painterResource

internal expect fun byteArrayToImageBitmap(byteArray: ByteArray): ImageBitmap
private val httpClient = HttpClient()

private sealed interface LoadState {
    data object Loading : LoadState
    data class Success(val bitmap: ImageBitmap) : LoadState
    data object Error : LoadState
}

@Composable
private fun rememberImageState(url: String): State<LoadState> {
    return produceState<LoadState>(initialValue = LoadState.Loading, key1 = url) {
        value = try {
            val bytes = httpClient.get(url).body<ByteArray>()
            LoadState.Success(byteArrayToImageBitmap(bytes))
        } catch (e: Exception) {
            e.printStackTrace()
            LoadState.Error
        }
    }
}

@Composable
fun YoutubeVideoItem(
    video: YoutubeModel,
    onClick: () -> Unit
) {
    val imageState by rememberImageState(video.thumbnailUrl)

    Box(
        modifier = Modifier
            .width(284.dp)
            .height(160.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(Color.LightGray)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        when (val state = imageState) {
            is LoadState.Loading -> {
                CircularProgressIndicator()
            }
            is LoadState.Success -> {
                Image(
                    painter = BitmapPainter(state.bitmap),
                    contentDescription = video.title,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
            is LoadState.Error -> {
                Icon(
                    painter = painterResource(Res.drawable.img_example),
                    contentDescription = "Image load failed",
                    tint = Color.Gray
                )
            }
        }
    }
}