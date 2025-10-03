package com.modura.app.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import modura.composeapp.generated.resources.Res
import modura.composeapp.generated.resources.ic_chevron_right
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalResourceApi::class)
@Composable
fun CheckTextDetail(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    onDetailClick: () -> Unit,
    text: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onDetailClick() }
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        CheckText(
            checked = checked,
            onCheckedChange = onCheckedChange,
            text = text,
            modifier = Modifier.weight(1f)
        )

        Image(
            painter = painterResource(Res.drawable.ic_chevron_right),
            contentDescription = "자세히 보기",
            modifier = Modifier.size(20.dp)
        )
    }
}