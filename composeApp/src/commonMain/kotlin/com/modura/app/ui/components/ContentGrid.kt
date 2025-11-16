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
import androidx.compose.ui.unit.dp
import com.modura.app.ui.theme.Black
import com.modura.app.ui.theme.BlackTransparent
import com.modura.app.util.platform.rememberImageBitmapFromUrl
import modura.composeapp.generated.resources.*
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.math.sqrt

@Composable
fun ContentGrid(
    image: String = "",
    title: String = "제목",
    onClick: () -> Unit = {}
){
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

    Column(
        modifier = Modifier.fillMaxSize()
            .clickable(onClick = onClick),
    ){
        when {
            isLoading -> {
                Box(modifier = Modifier.fillMaxSize().background(Color.LightGray))
            }
            imageBitmap != null -> {
                Image(
                    bitmap = imageBitmap!!,
                    contentDescription = "이미지",
                    modifier = Modifier.fillMaxSize()
                        .aspectRatio(3f/4f)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )
            }
            else -> {
                Box(modifier = Modifier.fillMaxSize().background(Color.Gray)) {
                    Image(
                        painter = painterResource(Res.drawable.img_not_found),
                        contentDescription = "로드 실패",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(title,style = MaterialTheme.typography.labelLarge, modifier = Modifier.height(40.dp), color = MaterialTheme.colorScheme.onBackground)
    }
}