package com.modura.app.ui.screens.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import com.modura.app.LocalRootNavigator
import com.modura.app.ui.components.*
import com.modura.app.ui.screens.camera.StillcutScreen
import com.modura.app.ui.theme.*
import com.modura.app.util.platform.rememberImageBitmapFromUrl
import modura.composeapp.generated.resources.*
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

data class PlaceDetailScreen(val id: Int) : Screen {
    override val key: String = "ContentDetailScreen_$id"

    @Composable
    override fun Content() {

        val rootNavigator = LocalRootNavigator.current
        val screenModel: DetailScreenModel = getScreenModel()
        val detailUiState by screenModel.detailPlaceUiState
        val placeReviews by screenModel.placeReviews.collectAsState()
        var imageBitmap by remember { mutableStateOf<ImageBitmap?>(null) }
        var isLoading by remember { mutableStateOf(true) }

        LaunchedEffect(key1 = id) {
            screenModel.detailPlace(id)
            screenModel.placeReviews(id)
        }
        if (detailUiState.inProgress) {
            Box(
                modifier = Modifier.fillMaxSize().background(Gray100),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        if (detailUiState.errorMessage != null) {
            Box(
                modifier = Modifier.fillMaxSize().background(Gray100),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = detailUiState.errorMessage ?: "오류가 발생했습니다.",
                    color = Gray700,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            return
        }
        detailUiState.data?.let { placeData ->
            rememberImageBitmapFromUrl(
                url = placeData.placeImageUrl,
                onSuccess = { loadedBitmap ->
                    imageBitmap = loadedBitmap
                    isLoading = false
                },
                onFailure = { errorMessage ->
                    println("이미지 로드 실패: $errorMessage")
                    isLoading = false
                }
            )

            var internalIsLiked by remember { mutableStateOf(placeData.isLiked) }
            LaunchedEffect(placeData.isLiked) {
                internalIsLiked = placeData.isLiked
            }
            val bookmark =
                if (internalIsLiked) painterResource(Res.drawable.img_bookmark_big_selected) else painterResource(
                    Res.drawable.img_bookmark_big_unselected
                )

            LazyColumn(
                modifier = Modifier.fillMaxSize().background(Gray100)
            ) {
                item {
                    Column(
                        modifier = Modifier.fillMaxWidth()
                            .padding(top = 60.dp, bottom = 20.dp, start = 20.dp, end = 20.dp)
                    ) {
                        Row {
                            Icon(
                                painter = painterResource(Res.drawable.ic_back),
                                contentDescription = "뒤로가기",
                                modifier = Modifier.size(24.dp)
                                    .clickable {
                                        rootNavigator?.pop()
                                    }
                            )
                            Spacer(Modifier.weight(1f))
                            Icon(
                                painter = painterResource(Res.drawable.ic_camera),
                                contentDescription = "카메라",
                                modifier = Modifier.size(24.dp).clickable {
                                    rootNavigator?.push(StillcutScreen(id))
                                }
                            )
                        }
                        Spacer(Modifier.height(20.dp))
                        Text(placeData.name, style = MaterialTheme.typography.headlineLarge)
                        Spacer(Modifier.height(4.dp))
                        Row {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row {
                                    for (i in 1..5) {
                                        val fraction = when {
                                            placeData.reviewAvg  >= i.toDouble() -> 1.0
                                            placeData.reviewAvg  > (i - 1).toDouble() ->  placeData.reviewAvg  - (i - 1).toDouble()
                                            else -> 0.0
                                        }
                                        ReviewStar(fraction = fraction.toFloat())
                                    }
                                }
                            }
                            Spacer(Modifier.width(4.dp))
                            Text(
                                text = "(${placeData.reviewCount.toString()})",
                                style = MaterialTheme.typography.light8
                            )
                        }
                        Spacer(Modifier.height(4.dp))
                        Text("경기도", style = MaterialTheme.typography.labelSmall)
                        Spacer(Modifier.height(16.dp))
                            if (imageBitmap != null) {
                                Image(
                                    bitmap = imageBitmap!!,
                                    contentDescription = placeData.name,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .aspectRatio(16f / 9f)
                                        .shadow(
                                            elevation = 2.dp,
                                            shape = RoundedCornerShape(8.dp),
                                            clip = false
                                        )
                                        .clip(RoundedCornerShape(8.dp))
                                        .border(
                                            width = 0.5.dp,
                                            color = Color.White.copy(alpha = 0.3f),
                                            shape = RoundedCornerShape(8.dp)
                                        ),
                                    contentScale = ContentScale.Crop
                                )
                            } else {
                                Image(
                                    painter = painterResource(Res.drawable.img_not_found),
                                    contentDescription = "기본 이미지",
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .aspectRatio(16f / 9f)
                                        .clip(RoundedCornerShape(8.dp)),
                                    contentScale = ContentScale.Crop
                                )
                            }
                        }
                    }
                item {
                    Column {
                        Spacer(Modifier.height(20.dp))
                        Text(
                            modifier = Modifier
                                .padding(horizontal = 20.dp),
                            text = "${placeData.name}에서 촬영한 콘텐츠",
                            style = MaterialTheme.typography.titleMedium,
                        )
                        Spacer(Modifier.height(4.dp))
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            contentPadding = PaddingValues(horizontal = 20.dp)
                        ) {
                            items(placeData.contentList) { content ->
                                content?.let {
                                    ContentItemMiddle(
                                        id = it.contentId,
                                        bookmark = it.isLiked,
                                        image = it.thumbnail,
                                        title = it.title,
                                        onClick = {
                                            rootNavigator?.push(ContentDetailScreen(it.contentId))
                                        }
                                    )
                                }
                            }
                        }
                        Spacer(Modifier.height(20.dp))
                    }
                }
                item {
                    Column(
                        Modifier.background(White).fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                    ) {
                        Text(
                            "방문 후기를 남겨주세요!",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Gray700,
                            modifier = Modifier.padding(top = 12.dp)
                        )
                        var userRating by remember { mutableStateOf(0) }
                        ReviewStarInput(
                            rating = userRating,
                            onRatingChange = { newRating ->
                                userRating = newRating
                                rootNavigator?.push(
                                    ReviewScreen(
                                        1,
                                        "장소",
                                        placeData.name,
                                        newRating
                                    )
                                )
                            }
                        )
                        Spacer(Modifier.height(12.dp))
                    }
                }
                item {
                    Spacer(Modifier.height(4.dp))
                }
                item {
                    Column(
                        modifier = Modifier.background(White).fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        placeReviews.take(2).forEach { review ->
                            ReviewLocationList(
                                name = review.username,
                                score = review.rating,
                                date = review.createdAt,
                                text = review.comment,
                                image = review.imageUrl
                            )
                        }
                    }
                }
            }
        }
    }
}