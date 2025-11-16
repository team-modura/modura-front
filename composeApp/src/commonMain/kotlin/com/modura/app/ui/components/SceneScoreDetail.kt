package com.modura.app.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
 fun SceneScoreDetail(label: String, score: Double?) {
    val finalScore = score ?: 0.0
    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(label, modifier = Modifier.width(60.dp), style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onBackground)
        SceneScoreBar(
            progress = (finalScore / 100).toFloat(),
            modifier = Modifier.weight(1f)
        )
        Text(
            text = finalScore.toInt().toString(),
            modifier = Modifier.width(30.dp),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}