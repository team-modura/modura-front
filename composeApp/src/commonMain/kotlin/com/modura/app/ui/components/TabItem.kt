package com.modura.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.modura.app.ui.theme.Gray500
import com.modura.app.ui.theme.Main500

@Composable
fun TabItem(text: String, isSelected: Boolean, onClick: () -> Unit) {
    val textColor = if (isSelected) Main500 else Gray500
    val fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal

    var textWidth by remember { mutableStateOf(0.dp) }
    val density = LocalDensity.current

    Column(
        modifier = Modifier.clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = null,
            onClick = onClick),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = text,
            color = textColor,
            fontWeight = fontWeight,
            fontSize = 16.sp,
            modifier = Modifier.padding(start = 20.dp, top=20.dp, end = 20.dp),
            onTextLayout = { textLayoutResult ->
                textWidth = with(density) { textLayoutResult.size.width.toDp() }
            }
        )
        Spacer(modifier = Modifier.height(2.dp))
        if (isSelected) {
            Box(
                modifier = Modifier
                    .width(textWidth) // 선의 너비
                    .height(1.dp)  // 선의 두께
                    .background(Main500), // 선의 색상
            )
        }
    }
}