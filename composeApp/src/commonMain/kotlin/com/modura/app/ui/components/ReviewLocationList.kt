package com.modura.app.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.modura.app.ui.theme.*
import com.modura.app.util.platform.rememberImageBitmapFromUrl
import modura.composeapp.generated.resources.Res
import modura.composeapp.generated.resources.img_example
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ReviewLocationList(
    name: String,
    score: Int,
    date: String,
    text: String,
    image: List<String>
){
    Column(Modifier.padding(vertical = 10.dp), verticalArrangement = Arrangement.spacedBy(4.dp)){
        Row(Modifier.padding(horizontal = 20.dp), horizontalArrangement = Arrangement.spacedBy(4.dp), verticalAlignment = Alignment.CenterVertically){
            Row{
                for (i in 1..5) {
                    val fraction = when {
                        score >= i -> 1f
                        score > i - 1 -> score - (i - 1)
                        else -> 0f
                    }
                    ReviewStar(fraction.toFloat())
                }
            }
            Text(name, style = MaterialTheme.typography.labelSmall, color = Gray800)
            Text("(${date})", style = MaterialTheme.typography.labelSmall, color = Gray800)
        }
        Text(text, style = MaterialTheme.typography.bodySmall, color = Gray800, modifier = Modifier.padding(horizontal = 20.dp))
        Spacer(Modifier.height(4.dp))
        if (image.isNotEmpty()) {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(horizontal = 20.dp)
            ) {
                items(image) { imageUrl ->
                    ReviewImageItem(imageUrl = imageUrl)
                }
            }
        }
    }
}
/*
@Composable
@Preview
private fun preview(){
    ReviewList(
        name = "김승혁",
        score = 4.5f,
        date = "2023.07.15",
        text = "아이들에 대한 묘사가 너무 절묘하다. 유난히 똑똑한 아이들임을 보여주면서도 순진함 속에 그들은 그들만의 규칙이 있다. 어리다고 미성숙하게만 그리는 게 아니라 하나의 인격체로서 다룸. 일레븐한테 가발 씌우는 의미 불명의 행태를 빼면 굉장히 재밌게 봤다. 시즌 2가 나와주길 기대."
    )
}*/
