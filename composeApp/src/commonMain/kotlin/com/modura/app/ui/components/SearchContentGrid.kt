package com.modura.app.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.modura.app.ui.theme.Black
import com.modura.app.ui.theme.BlackTransparent
import modura.composeapp.generated.resources.*
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.math.sqrt

@Composable
fun SearchContentGrid(
    image: String = "",
    bookmark: Boolean = false,
    onClick: () -> Unit = {}
){
    val bookmark=if(bookmark) painterResource(Res.drawable.img_bookmark_big_selected) else painterResource(Res.drawable.img_bookmark_big_unselected)

    Box(
        modifier = Modifier.fillMaxSize()
            .clickable(onClick = onClick),
    ){
        Image(
            painter = painterResource(Res.drawable.img_example),
            contentDescription = "이미지",
            modifier = Modifier.fillMaxSize()
                .aspectRatio(210f/297f)
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop
        )

        Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp)) {
            Spacer(Modifier.weight(1f))
            Icon(
                painter = bookmark,
                contentDescription = "북마크",
                tint = Color.Unspecified,
                modifier = Modifier
                    .width(20.dp)
                    .height(36.dp)
                    .clickable {
                    }
            )
        }
    }
}