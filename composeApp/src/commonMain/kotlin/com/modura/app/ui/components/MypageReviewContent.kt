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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.modura.app.ui.theme.*
import modura.composeapp.generated.resources.Res
import modura.composeapp.generated.resources.img_example
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.math.max

@Composable
fun MypageReviewContent(
    type: String,
    title: String,
    name: String,
    score: Float,
    date: String,
    text: String,
    onClick: () -> Unit = {}
) {
    val backgroundImage: Painter = painterResource(Res.drawable.img_example)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .drawWithContent {
                val painter = backgroundImage
                val paddingInPx = with(density) { 12.dp.toPx() }  //dp => px
                val backgroundSize = androidx.compose.ui.geometry.Size( this.size.width + (paddingInPx * 2) , this.size.height + (paddingInPx * 2))
                val scaleFactor = ContentScale.Crop.computeScaleFactor(srcSize = painter.intrinsicSize, dstSize = backgroundSize)
                val finalScale = max(scaleFactor.scaleX, scaleFactor.scaleY)
                val dx = (painter.intrinsicSize.width * finalScale - size.width) / 2f
                val dy = (painter.intrinsicSize.height * finalScale - size.height) / 2f
                withTransform({
                    translate(left = +paddingInPx, top = -paddingInPx)
                    scale(scaleX = finalScale, scaleY = finalScale)
                    translate(left = +dx, top = -dy)
                }) {
                    with(painter) {
                        draw(size = painter.intrinsicSize)
                    }
                }

                translate(left = -paddingInPx, top = -paddingInPx) {
                    drawRect(color = Black50P, size = backgroundSize)
                }
                drawContent()
            }
            .clickable(onClick = onClick)
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Spacer(Modifier.height(16.dp))
        Text(
            text = title,
            color = Color.White,
            style = MaterialTheme.typography.titleMedium
        )
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
                    ReviewStar(fraction, color = Gray100)
                }
            }
            Text(name, style = MaterialTheme.typography.labelSmall, color = Gray100)
            Text("(${date})", style = MaterialTheme.typography.labelSmall, color = Gray100)
        }
        Text(
            text,
            style = MaterialTheme.typography.bodySmall,
            color = Gray100
        )
    }
}