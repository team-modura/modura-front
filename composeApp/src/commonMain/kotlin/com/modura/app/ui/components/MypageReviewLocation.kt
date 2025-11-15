package com.modura.app.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.modura.app.ui.theme.*
import com.modura.app.util.platform.rememberImageBitmapFromUrl
import modura.composeapp.generated.resources.Res
import modura.composeapp.generated.resources.img_example
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.math.max

@Composable
fun MypageReviewLocation(
    id: Int,
    placeId: Int,
    thumbnail:String,
    title: String,
    name: String,
    score: Int,
    date: String,
    text: String,
    image: List<String>,
    onClick: () -> Unit = {}
) {
    println(image)
    var imageBitmap by remember { mutableStateOf<ImageBitmap?>(null) }
    rememberImageBitmapFromUrl(
        url = thumbnail,
        onSuccess = { loadedBitmap -> imageBitmap = loadedBitmap },
        onFailure = { errorMessage -> println("이미지 로드 실패: $errorMessage") }
    )
    val density = LocalDensity.current
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .drawWithContent {
                imageBitmap?.let { bitmap ->
                    val dstSize = this.size
                    val srcSize = Size(bitmap.width.toFloat(), bitmap.height.toFloat())

                    val scaleFactor = ContentScale.Crop.computeScaleFactor(srcSize, dstSize)
                    val finalScale = max(scaleFactor.scaleX, scaleFactor.scaleY)

                    val scaledWidth = srcSize.width * finalScale
                    val scaledHeight = srcSize.height * finalScale
                    val dx = (dstSize.width - scaledWidth) / 2.0f
                    val dy = (dstSize.height - scaledHeight) / 2.0f

                    drawImage(
                        image = bitmap,
                        dstOffset = androidx.compose.ui.unit.IntOffset(dx.toInt(), dy.toInt()),
                        dstSize = androidx.compose.ui.unit.IntSize(
                            scaledWidth.toInt(),
                            scaledHeight.toInt()
                        )
                    )
                    drawRect(color = Black50P, size = dstSize)
                }
                drawContent()
            }
            .clickable(onClick = onClick)
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Spacer(Modifier.height(16.dp))
        Column {
            Text(
                text = title,
                color = Color.White,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(Modifier.height(4.dp))
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row {
                for (i in 1..5) {
                    val fraction = when {
                        score >= i -> 1f
                        score > i - 1 -> score - (i - 1)
                        else -> 0f
                    }
                    ReviewStar(fraction.toFloat(), color = Gray100)
                }
            }
            Text(name, style = MaterialTheme.typography.labelSmall, color = Gray100)
            Text("(${date})", style = MaterialTheme.typography.labelSmall, color = Gray100)
        }
        Text(
            text,
            style = MaterialTheme.typography.bodySmall,
            color = Gray100,
        )
        Spacer(Modifier.height(4.dp))
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(image) { imageUrl ->
                ReviewImageItem(imageUrl)
            }
        }
    }
}