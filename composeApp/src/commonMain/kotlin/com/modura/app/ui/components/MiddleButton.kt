package com.modura.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun MiddleButton(
    text: String = "확인",
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    val gradientBrush = if (enabled) {
        Brush.verticalGradient(
            colors = listOf(Color(0xFF90D8D8), Color(0xFFCADBDB))
        )
    } else {
        Brush.verticalGradient(
            colors = listOf(Color(0xFFDBDBDB), Color(0xFFADADAD))
        )
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(64.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(brush = gradientBrush)
            .clickable(enabled = enabled, onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(text = text, style = MaterialTheme.typography.titleLarge,color = MaterialTheme.colorScheme.surface )
    }
}