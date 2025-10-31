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
import modura.composeapp.generated.resources.*
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.math.sqrt

@Composable
fun ContentItemSmall(
    bookmark: Boolean = false,
    ott: List<String> = listOf("netflix", "watcha"),
    image: String = "",
    title: String = "제목",
    rank: String = "순위",
    onClick: () -> Unit = {}
){
    /*val painter = if (image.isNotBlank()) { rememberAsyncImagePainter(image)
    } else { painterResource(Res.drawable.img_example) }*/
    val bookmark=if(bookmark) painterResource(Res.drawable.img_bookmark_big_selected) else painterResource(Res.drawable.img_bookmark_big_unselected)

    Box(
        modifier = Modifier
            .width(105.dp)
            .height(148.dp)
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
                .fillMaxHeight(0.5f)
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
                        .width(20.dp)
                        .height(36.dp)
                        .clickable{
                            //클릭하면 북마크 되도록 수정
                        }
                )
                Row(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(vertical = 10.dp) ,
                    horizontalArrangement = Arrangement.spacedBy(3.dp)
                ) {
                    ott.forEach { platform ->
                        when(platform) {
                            "netflix" -> Image(painter = painterResource(Res.drawable.img_netflix), contentDescription = "Netflix", modifier = Modifier.size(16.dp))
                            "watcha" -> Image(painter = painterResource(Res.drawable.img_watcha), contentDescription = "Watcha", modifier = Modifier.size(16.dp))
                            "disney" -> Image(painter = painterResource(Res.drawable.img_disney), contentDescription = "disney", modifier = Modifier.size(16.dp))
                            "coopang" -> Image(painter = painterResource(Res.drawable.img_coopang), contentDescription = "coopang", modifier = Modifier.size(16.dp))
                            "wave" -> Image(painter = painterResource(Res.drawable.img_wave),contentDescription = "wave", modifier = Modifier.size(16.dp))
                            "tving" -> Image(painter = painterResource(Res.drawable.img_tving), contentDescription = "tving", modifier = Modifier.size(16.dp))
                            else -> {}
                        }
                    }
                }
            }
            Box(modifier = Modifier.weight(1f))
            Column(
                modifier = Modifier.padding(5.dp)
            ) {
                Text(
                    text = rank,
                    color = Color.White,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = title,
                    color = Color.White,
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}

@Preview
@Composable
fun Preview(){
    ContentItemSmall(
        bookmark = false,
        image = "",
        title = "기묘한 이야기",
        rank = "1",
        onClick = { /* 프리뷰에서는 비워둬도 됩니다 */ }
    )
}