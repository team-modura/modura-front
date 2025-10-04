package com.modura.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.modura.app.model.WelfareBenefit
import com.modura.app.ui.theme.Black
import com.modura.app.ui.theme.Gray500
import com.modura.app.ui.theme.Gray600
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.daysUntil
import kotlinx.datetime.todayIn
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
@Composable
fun WelfareListItem(
    item: WelfareBenefit,
    onItemClick: (WelfareBenefit) -> Unit,
    modifier: Modifier = Modifier
) {
    val today = Clock.System.todayIn(TimeZone.currentSystemDefault())
    val daysUntil = today.daysUntil(item.endDate)
    val dDayText = when {
        daysUntil < 0 -> "기간 만료"
        daysUntil == 0 -> "D-Day"
        else -> "D-$daysUntil"
    }
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White)
            .clickable { onItemClick(item) }
            .padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = item.region,
                color = Gray500,
                style = MaterialTheme.typography.labelSmall
            )
            Text(
                text = dDayText,
                color = Gray500,
                style = MaterialTheme.typography.labelSmall
            )
        }

        Text(
            text = item.name,
            color = Black,
            style = MaterialTheme.typography.titleLarge
        )

        // 5. 지원 금액 및 주요 대상
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            InfoRow(title = "지원 금액", content = item.supportAmount)
            InfoRow(title = "주요 대상", content = item.targetAudience)
        }
    }
}

@Composable
private fun InfoRow(title: String, content: String) {
    Row(verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp) ) {
        Text(
            text = title,
            color = Black,
            style = MaterialTheme.typography.bodySmall
        )
        Text(
            text = content,
            color = Gray600,
            style = MaterialTheme.typography.bodySmall
        )
    }
}