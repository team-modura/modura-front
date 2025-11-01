package com.modura.app.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import modura.composeapp.generated.resources.Res
import modura.composeapp.generated.resources.img_bookmark_big_selected
import modura.composeapp.generated.resources.img_bookmark_big_unselected
import modura.composeapp.generated.resources.img_example
import org.jetbrains.compose.resources.painterResource

@Composable
fun ListLocationItem(
    bookmark: Boolean = false,
    id: Int,
    image: String,
    title: String,
    location: String,
    region: String,
    rank: String,
    onClick: (String) -> Unit
){
    val bookmark=if(bookmark) painterResource(Res.drawable.img_bookmark_big_selected) else painterResource(Res.drawable.img_bookmark_big_unselected)

    Row(Modifier.fillMaxWidth()
        .clickable { onClick(title) }
        , verticalAlignment = Alignment.CenterVertically){
        Box(
            modifier = Modifier
                .width(100.dp)
                .height(60.dp)
                .clip(RoundedCornerShape(8.dp))
        ) {
            Image(
                painter = painterResource(Res.drawable.img_example), //추후 api 내부에 있는 이미지로 수정
                contentDescription = "이미지",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            Icon(
                painter = bookmark,
                contentDescription = "북마크",
                tint = Color.Unspecified,
                modifier = Modifier
                    .width(13.dp)
                    .height(23.dp)
                    .clickable {}
                    .padding(end=10.dp)
                    .align(Alignment.TopEnd )
            )
        }
        Spacer(Modifier.width(12.dp))
        Column{
            Text(title, style = MaterialTheme.typography.bodyMedium)
            Spacer(Modifier.height(4.dp))
            Row{
                Text(location, style = MaterialTheme.typography.labelMedium)
                Spacer(Modifier.weight(1f))
                Text(region, style = MaterialTheme.typography.labelSmall)
            }
        }
    }
}