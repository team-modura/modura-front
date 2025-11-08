package com.modura.app.ui.components

import androidx.compose.animation.core.copy
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import modura.composeapp.generated.resources.Res
import modura.composeapp.generated.resources.ic_delete
import org.jetbrains.compose.resources.painterResource

@Composable
fun PhotoItem(
    photoUri: String,
    onDelete: () -> Unit
) {
    Box(
        modifier = Modifier.size(100.dp)
    ) {
        Image(
            painter = rememberAsyncImagePainter(model = photoUri), // photoUri를 모델로 사용
            contentDescription = "선택된 사진",
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop
        )
        // 삭제 버튼
        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(4.dp)
                .size(20.dp)
                .clip(androidx.compose.foundation.shape.RoundedCornerShape(10.dp))
                .background(Color.Black.copy(alpha = 0.5f))
                .clickable(onClick = onDelete),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(Res.drawable.ic_delete),
                contentDescription = "사진 삭제",
                modifier = Modifier.size(12.dp),
                tint = Color.White
            )
        }
    }
}