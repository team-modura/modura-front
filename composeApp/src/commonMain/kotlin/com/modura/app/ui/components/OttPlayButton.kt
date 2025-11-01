package com.modura.app.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.modura.app.ui.theme.Gray500
import com.modura.app.ui.theme.Green700
import com.modura.app.ui.theme.White
import modura.composeapp.generated.resources.*
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OttPlayButton(
    ott: String,
    onClick: () -> Unit
) {
    val logoResource: DrawableResource? = when (ott) {
        "netflix" -> Res.drawable.img_netflix
        "watcha" -> Res.drawable.img_watcha
        "disney" -> Res.drawable.img_disney
        "coupang" -> Res.drawable.img_coupang
        "wave" -> Res.drawable.img_wave
        "tving" -> Res.drawable.img_tving
        else -> null
    }

    val textResource: String? = when(ott) {
        "netflix" -> "넷플릭스"
        "watcha" -> "왓챠"
        "disney" -> "디즈니 플러스"
        "coopang" -> "쿠팡플레이"
        "wave" -> "웨이브"
        "tving" -> "티빙"
        else -> null
    }

    if (logoResource != null && textResource != null) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = onClick)
                .background(White),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(Modifier.padding(10.dp,5.dp),verticalAlignment = Alignment.CenterVertically ){
                Image(
                    painter = painterResource(logoResource),
                    contentDescription = "$ott 로고",
                    modifier = Modifier.height(24.dp)
                )

                Spacer(Modifier.width(12.dp))

                Text(
                    text = textResource,
                    color = Color.Black,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Spacer(Modifier.weight(1f))

            Icon(
                painter = painterResource(Res.drawable.ic_play),
                contentDescription = "재생",
                modifier = Modifier.size(16.dp)
            )
            Spacer(Modifier.width(10.dp))
        }
    }
}