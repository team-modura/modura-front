package com.modura.app.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.modura.app.data.dev.PlaceInfo
import com.modura.app.domain.model.response.map.PlaceResponseModel
import com.modura.app.ui.theme.Gray100
import com.modura.app.ui.theme.Gray700
import com.modura.app.ui.theme.light8
import com.modura.app.util.platform.rememberImageBitmapFromUrl
import modura.composeapp.generated.resources.Res
import modura.composeapp.generated.resources.img_bookmark_big_selected
import modura.composeapp.generated.resources.img_bookmark_big_unselected
import modura.composeapp.generated.resources.img_example
import modura.composeapp.generated.resources.img_not_found
import org.jetbrains.compose.resources.painterResource


@Composable
fun ListMapItem(
    place: PlaceResponseModel,
    onClick: (Int) -> Unit
) {
    var imageBitmap by remember { mutableStateOf<ImageBitmap?>(null) }
    var isLoading by remember { mutableStateOf(true) }

    rememberImageBitmapFromUrl(
        url = place.thumbnail ?: "",
        onSuccess = { loadedBitmap ->
            imageBitmap = loadedBitmap
            isLoading = false
        },
        onFailure = { errorMessage ->
            println("이미지 로드 실패: $errorMessage")
            isLoading = false
        }
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick(place.id) },
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (isLoading || imageBitmap == null) {
            Image(
                modifier = Modifier
                    .height(80.dp)
                    .width(80.dp)
                    .clip(RoundedCornerShape(8.dp)),
                painter = painterResource(Res.drawable.img_not_found),
                contentDescription = null,
                contentScale = ContentScale.Crop
                )
        } else {
            Image(
                bitmap = imageBitmap!!,
                contentDescription = null,
                modifier = Modifier.height(80.dp).width(80.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
        }
        Spacer(Modifier.width(8.dp))
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                if (place.isLiked) {
                    Icon(
                        painter = painterResource(Res.drawable.img_bookmark_big_selected),
                        contentDescription = "북마크",
                        tint = Color.Unspecified,
                        modifier = Modifier
                            .width(7.dp)
                            .height(12.dp)
                            .clickable {}
                    )
                    Spacer(Modifier.width(4.dp))
                }
                Text(place.name, style = MaterialTheme.typography.bodyMedium)
            }
            Spacer(Modifier.height(4.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(place.rating.toString(), style = MaterialTheme.typography.titleSmall)
                Spacer(Modifier.width(4.dp))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row {
                        for (i in 1..5) {
                            val fraction = when {
                                place.rating >= i -> 1f
                                place.rating > i - 1 -> place.rating - (i - 1)
                                else -> 0f
                            }
                            ReviewStar(fraction.toFloat())
                        }
                    }
                }
                Spacer(Modifier.width(4.dp))
                Text(
                    text = "(${place.reviewCount.toString()})",
                    style = MaterialTheme.typography.light8
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            /*Row{
                    Text(distanceText, style = MaterialTheme.typography.labelSmall)
                    Spacer(Modifier.width(12.dp))
                    Text(text = place.address, style = MaterialTheme.typography.bodySmall)
                }*/
            Spacer(modifier = Modifier.height(12.dp))
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                items(place.content.size) {
                    Text(
                        text = place.content[it],
                        style = MaterialTheme.typography.titleSmall,
                        color = Gray700,
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(Gray100)
                            .padding(horizontal = 4.dp, vertical = 2.dp)
                    )
                }
            }
        }
    }
}