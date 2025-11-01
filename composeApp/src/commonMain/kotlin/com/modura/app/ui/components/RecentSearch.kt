package com.modura.app.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import modura.composeapp.generated.resources.Res
import modura.composeapp.generated.resources.ic_x
import org.jetbrains.compose.resources.painterResource

@Composable
fun RecentSearch(
    term: String,
    onClick: () -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = term,
            style = MaterialTheme.typography.bodyMedium,
        )
        Icon(
            painterResource(Res.drawable.ic_x),
            contentDescription = "delete recent search",
            modifier = Modifier.clickable(onClick = onDelete)
        )
    }
}
