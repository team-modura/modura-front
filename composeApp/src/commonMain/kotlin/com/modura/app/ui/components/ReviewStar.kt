package com.modura.app.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.modura.app.ui.theme.*
import modura.composeapp.generated.resources.Res
import modura.composeapp.generated.resources.ic_star_fill
import modura.composeapp.generated.resources.ic_star_unfill
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ReviewStar(
    fraction: Float,
    modifier: Modifier = Modifier.size(12.dp),
    color: Color = Main500
) {
    Box(modifier = modifier) {
        Image(
            painter = painterResource(Res.drawable.ic_star_unfill),
            contentDescription = "빈 별",
            modifier = Modifier.fillMaxSize(),
            colorFilter = ColorFilter.tint(color)
        )

        if (fraction > 0f) {
            Image(
                painter = painterResource(Res.drawable.ic_star_fill),
                contentDescription = "채워진 별",
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RectangleShape)
                    .clip(FractionalClip(fraction)),
                colorFilter = ColorFilter.tint(color)
            )
        }
    }
}
private fun FractionalClip(fraction: Float) = object : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        return Outline.Rectangle(
            Rect(
                left = 0f,
                top = 0f,
                right = size.width * fraction,
                bottom = size.height
            )
        )
    }
}