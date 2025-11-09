package com.modura.app.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.modura.app.data.dev.SceneInfo

@Composable
fun StillcutItem(scene: SceneInfo, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp) // 아이템의 높이를 적절히 지정
            .padding(horizontal = 16.dp) // 좌우 여백
            .clip(RoundedCornerShape(12.dp)) // 모서리를 둥글게
            .clickable(onClick = onClick)
    ) {
        // 배경 이미지 (Coil의 AsyncImage 사용)
        AsyncImage(
            model = scene.imageResId, // String 타입의 URL을 바로 사용
            contentDescription = scene.title,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop // 이미지가 Box를 꽉 채우도록
        )

        // 이미지 위에 표시될 제목
        Text(
            text = scene.title,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            modifier = Modifier
                .align(Alignment.BottomStart) // 왼쪽 하단에 정렬
                .padding(16.dp) // 안쪽 여백
        )
    }
}