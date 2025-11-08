package com.modura.app.ui.components

import androidx.compose.animation.core.copy
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import com.modura.app.ui.theme.Gray300
import modura.composeapp.generated.resources.Res
import modura.composeapp.generated.resources.ic_camera
import modura.composeapp.generated.resources.ic_delete
import org.jetbrains.compose.resources.painterResource

@Composable
fun PhotoAddButton(
    count: Int,
    limit: Int,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(100.dp)
            .border(1.dp, Gray300, RoundedCornerShape(8.dp))
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                painter = painterResource(Res.drawable.ic_camera),
                contentDescription = "사진 추가",
                modifier = Modifier.size(40.dp),
                tint = Gray300
            )
            Row{
                Text(text = "사진", style = MaterialTheme.typography.bodyMedium, color = Gray300)
                Text(text = "$count/$limit", style = MaterialTheme.typography.bodyMedium, color = Gray300)
            }
        }
    }
}