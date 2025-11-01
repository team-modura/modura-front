package com.modura.app.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun ReviewStarInput(
    rating: Int,
    onRatingChange: (Int) -> Unit,
    starSize: Dp = 48.dp // 기본 크기를 32dp로 설정
) {
    Row {
        for (i in 1..5) {
            val fraction = if (i <= rating) 1f else 0f

            ReviewStar(
                fraction = fraction,
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .size(starSize)
                    .clickable {
                        onRatingChange(i)
                    }
            )
        }
    }
}