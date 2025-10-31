package com.modura.app.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import com.modura.app.ui.theme.light8
import modura.composeapp.generated.resources.*
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.math.sqrt

@Composable
fun LocationItemSmall(
    bookmark: Boolean = false,
    rank: String = "0",
    title: String = "제목",
    region: String = "지역",
    location: String = "장소",
    image: String = "",
    onClick: () -> Unit = {}
){
    /*val painter = if (image.isNotBlank()) { rememberAsyncImagePainter(image)
    } else { painterResource(Res.drawable.img_example) }*/
    val bookmark=if(bookmark) painterResource(Res.drawable.img_bookmark_big_selected) else painterResource(Res.drawable.img_bookmark_big_unselected)

    Box(
        modifier = Modifier
            .width(150.dp)
            .height(100.dp)
            .clip(RoundedCornerShape(8.dp))
    ){
        Image(
            painter = painterResource(Res.drawable.img_example), //추후 api 내부에 있는 이미지로 수정
            contentDescription = "이미지",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.3f)
                .align(Alignment.BottomCenter)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(BlackTransparent, Black),
                        endY = Float.POSITIVE_INFINITY
                    )
                )
        )
        Column(modifier = Modifier.fillMaxSize()){
            Box(modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp)){
                Icon(
                    painter = bookmark,
                    contentDescription = "북마크",
                    tint = Color.Unspecified,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .width(13.dp)
                        .height(23.dp)
                        .clickable{
                            //클릭하면 북마크 되도록 수정
                        }
                )
            }
            Box(modifier = Modifier.weight(1f))
            Row(modifier = Modifier.padding(5.dp), horizontalArrangement = Arrangement.spacedBy(5.dp)){
                Text(
                    text = rank,
                    color = Color.White,
                    style = MaterialTheme.typography.titleMedium
                )
                Column {
                    Text(
                        text = title,
                        color = Color.White,
                        style = MaterialTheme.typography.light8
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ){
                        Text(
                            text = location,
                            color = Color.White,
                            style = MaterialTheme.typography.labelSmall
                        )
                        Box(modifier = Modifier.weight(1f))
                        Text(
                            text = region,
                            color = Color.White,
                            style = MaterialTheme.typography.light8
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun Preview(){
    LocationItemSmall(
        bookmark = false,
        image = "",
        title = "기묘한 이야기",
    )
}