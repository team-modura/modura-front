package com.modura.app.ui.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
 fun SectionTitle(title: String) {
    Spacer(Modifier.height(20.dp))
    Text(
        modifier = Modifier.padding(horizontal = 20.dp),
        text = title,
        style = MaterialTheme.typography.titleMedium,
    )
    Spacer(Modifier.height(5.dp))
}