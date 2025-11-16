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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.modura.app.ui.theme.Black
import com.modura.app.ui.theme.BlackTransparent
import com.modura.app.ui.theme.White
import com.modura.app.ui.theme.light8
import com.modura.app.util.platform.rememberImageBitmapFromUrl
import modura.composeapp.generated.resources.*
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.math.sqrt

@Composable
fun LocationItemSmall(
    id: Int,
    bookmark: Boolean = false,
    rank: Int = 0,
    title: String = "제목",
    region: String = "지역",
    location: String = "장소",
    image: String = "",
    onClick: () -> Unit = {}
){
    /*val painter = if (image.isNotBlank()) { rememberAsyncImagePainter(image)
    } else { painterResource(Res.drawable.img_example) }*/
    val bookmark=if(bookmark) painterResource(Res.drawable.img_bookmark_big_selected) else painterResource(Res.drawable.img_bookmark_big_unselected)

    var imageBitmap by remember { mutableStateOf<ImageBitmap?>(null) }
    var isLoading by remember { mutableStateOf(true) }

    rememberImageBitmapFromUrl(
        url = image,
        onSuccess = { loadedBitmap ->
            imageBitmap = loadedBitmap
            isLoading = false
        },
        onFailure = { errorMessage ->
            println("이미지 로드 실패: $errorMessage")
            isLoading = false
        }
    )

    Box(
        modifier = Modifier
            .width(150.dp)
            .height(100.dp)
            .clip(RoundedCornerShape(8.dp))
            .clickable(onClick = onClick)
    ){
        when {
            isLoading -> {
                Box(modifier = Modifier.fillMaxSize().background(Color.LightGray))
            }
            imageBitmap != null -> {
                Image(
                    bitmap = imageBitmap!!,
                    contentDescription = title,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
            else -> {
                Box(modifier = Modifier.fillMaxSize().background(Color.Gray)) {
                    Icon(
                        painter = painterResource(Res.drawable.ic_x),
                        contentDescription = "로드 실패",
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.3f)
                .align(Alignment.BottomCenter)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf( BlackTransparent, Black),
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
            Row(modifier = Modifier.padding(5.dp), horizontalArrangement = Arrangement.spacedBy(5.dp), verticalAlignment = Alignment.Bottom){
                if(rank!=0){
                    Text(
                        text = rank.toString(),
                        color = White,
                        style = MaterialTheme.typography.titleMedium
                    )
                }
                Column {
                    if(title!="제목"){
                        Text(
                            text = title,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            color = White,
                            style = MaterialTheme.typography.light8
                        )
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ){
                        Text(
                            text = location,
                            color = White,
                            style = MaterialTheme.typography.labelSmall
                        )
                        Box(modifier = Modifier.weight(1f))
                        if(region!="지역"){
                            Text(
                                text = region,
                                color = White,
                                style = MaterialTheme.typography.light8
                            )
                        }
                    }
                }
            }
        }
    }
}