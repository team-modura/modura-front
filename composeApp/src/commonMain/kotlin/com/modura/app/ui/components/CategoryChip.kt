package com.modura.app.ui.components

import androidx.compose.animation.core.copy
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key.Companion.I
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.size
import androidx.compose.ui.unit.sp
import com.modura.app.data.dev.Category
import com.modura.app.ui.theme.*
import modura.composeapp.generated.resources.Res
import modura.composeapp.generated.resources.img_cast_example
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun CategoryChip(
    category: Category,
    isSelected: Boolean,
    modifier: Modifier = Modifier
) {
    val backgroundColor = if (isSelected) Main500 else White
    val textColor = if (isSelected) White else Gray800

    Box(modifier = modifier) {
        Box(modifier = Modifier
            .matchParentSize()
            .padding(top = 1.dp, start = 2.dp)
            .clip(RoundedCornerShape(999.dp))
        )
        Row(
            modifier = modifier
                .shadow(
                    elevation = 2.dp,
                    shape = RoundedCornerShape(999.dp),
                    ambientColor = Color.Black.copy(alpha = 0.1f),
                    spotColor = Color.Black.copy(alpha = 0.1f),
                )
                .clip(RoundedCornerShape(999.dp))
                .background(backgroundColor)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(category.emoji, fontSize = 16.sp)
            Spacer(modifier = Modifier.width(4.dp))
            Text(category.categoryKor, style = MaterialTheme.typography.bodyMedium, color = textColor)
        }
    }
}