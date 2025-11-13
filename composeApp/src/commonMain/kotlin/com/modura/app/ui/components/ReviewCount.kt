package com.modura.app.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.Yellow
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import com.modura.app.ui.theme.*
import modura.composeapp.generated.resources.Res
import modura.composeapp.generated.resources.ic_star_fill
import modura.composeapp.generated.resources.ic_star_unfill
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ReviewCount(
    num: Int,
    count: Int,
    maxReviews: Int
){
    val fraction = if (maxReviews > 0) {
        count.toFloat() / maxReviews.toFloat()
    } else {
        0f
    }

    Row(horizontalArrangement = Arrangement.spacedBy(15.dp), verticalAlignment = Alignment.CenterVertically){
        Text("${num}점",style = MaterialTheme.typography.bodySmall, color = Gray800,modifier=Modifier.width(20.dp))
        Box(
            modifier = Modifier.weight(1f)
                .height(6.dp)
                .clip(RoundedCornerShape(99.dp))
                .background(White)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(fraction)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(99.dp))
                    .background(Main500)
            )
        }
        Text(count.toString(),style = MaterialTheme.typography.labelSmall, color = Gray800)
    }
}