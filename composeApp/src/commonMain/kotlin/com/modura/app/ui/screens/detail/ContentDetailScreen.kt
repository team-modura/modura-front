package com.modura.app.ui.screens.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import com.modura.app.LocalRootNavigator
import com.modura.app.ui.components.*
import com.modura.app.ui.theme.*
import com.modura.app.util.platform.rememberImageBitmapFromUrl
import modura.composeapp.generated.resources.*
import org.jetbrains.compose.resources.painterResource


data class ContentDetailScreen(val id: Int) : Screen {
    override val key: String = "ContentDetailScreen_$id"

    @Composable
    override fun Content() {

        val screenModel: DetailScreenModel = getScreenModel()
        val detailUiState by screenModel.detailUiState
        val youtubeUiState by screenModel.youtubeUiState
        val rootNavigator = LocalRootNavigator.current
        val contentReviews by screenModel.contentReviews.collectAsState()
        var imageBitmap by remember { mutableStateOf<ImageBitmap?>(null) }
        var isLoading by remember { mutableStateOf(true) }

        LaunchedEffect(key1 = id) {
            screenModel.detailContent(id)
            screenModel.contentReviews(id)
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
        detailUiState.data?.let { contentData ->
            rememberImageBitmapFromUrl(
                url = contentData.thumbnail,
                onSuccess = { loadedBitmap ->
                    imageBitmap = loadedBitmap
                    isLoading = false
                },
                onFailure = { errorMessage ->
                    println("이미지 로드 실패: $errorMessage")
                    isLoading = false
                }
            )

            var internalIsLiked by remember { mutableStateOf(contentData.isLiked) }
            LaunchedEffect(contentData.isLiked) {
                internalIsLiked = contentData.isLiked
            }
            val bookmark=if(internalIsLiked) painterResource(Res.drawable.img_bookmark_big_selected) else painterResource(Res.drawable.img_bookmark_big_unselected)

            BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
                val initialImageHeight = this.maxHeight
                val minImageHeight = 240.dp
                val lazyListState = rememberLazyListState()
                val density = LocalDensity.current

                val initialImageHeightPx = with(density) { initialImageHeight.toPx() }
                val minImageHeightPx = with(density) { minImageHeight.toPx() }

                val scrollOffset by remember {
                    derivedStateOf {
                        if (lazyListState.firstVisibleItemIndex == 0) {
                            lazyListState.firstVisibleItemScrollOffset.toFloat()
                        } else {
                            initialImageHeightPx - minImageHeightPx
                        }
                    }
                }

                val currentImageHeightPx =
                    (initialImageHeightPx - scrollOffset).coerceAtLeast(minImageHeightPx)
                val currentImageHeight = with(density) { currentImageHeightPx.toDp() }

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Gray100)
                ) {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        state = lazyListState
                    ) {
                        item {
                            Spacer(modifier = Modifier.height(initialImageHeight))
                        }
                        item {
                            Column(
                                modifier = Modifier
                                    .background(Gray100)
                            ) {
                                Column {
                                    Spacer(Modifier.height(20.dp))
                                    Text(
                                        "줄거리",
                                        style = MaterialTheme.typography.titleLarge,
                                        modifier = Modifier.padding(horizontal = 20.dp)
                                    )
                                    Spacer(Modifier.height(8.dp))
                                    Text(
                                        contentData.plot,
                                        style = MaterialTheme.typography.bodySmall,
                                        modifier = Modifier.padding(horizontal = 20.dp)
                                    )
                                    Spacer(Modifier.height(12.dp))
                                    Column(
                                        verticalArrangement = Arrangement.spacedBy(4.dp),
                                        modifier = Modifier.padding(horizontal = 20.dp)
                                    ) {
                                        if(contentData.platforms!=null){
                                            contentData.platforms.forEach { platform ->
                                                OttPlayButton(
                                                    ott = platform.toString(),
                                                    onClick = {
                                                        println("$platform PLAY 버튼 클릭됨!")
                                                    }
                                                )
                                            }
                                        }
                                    }
                                    Column(Modifier.padding(vertical = 12.dp)) {
                                        Row(modifier = Modifier.padding(horizontal = 20.dp), verticalAlignment = Alignment.Bottom) {
                                            Text("리뷰", style = MaterialTheme.typography.titleLarge)
                                            Spacer(Modifier.weight(1f))
                                            Text("전체보기", style = MaterialTheme.typography.bodySmall)
                                        }
                                        Spacer(Modifier.height(4.dp))
                                        Row(modifier = Modifier.padding(horizontal = 20.dp)) {
                                            Box(
                                                modifier = Modifier.background(White)
                                                    .clip(RoundedCornerShape(8.dp)),
                                                contentAlignment = Alignment.Center
                                            ) {
                                                Review(
                                                    contentData.reviewAvg,
                                                    listOf(
                                                        contentData.fiveStarCount,
                                                        contentData.fourStarCount,
                                                        contentData.threeStarCount,
                                                        contentData.twoStarCount,
                                                        contentData.oneStarCount
                                                    )
                                                )
                                            }
                                        }
                                        Spacer(Modifier.height(20.dp))
                                        Column(
                                            Modifier.background(White).fillMaxWidth(),
                                            horizontalAlignment = Alignment.CenterHorizontally,
                                            verticalArrangement = Arrangement.spacedBy(4.dp),
                                        ) {
                                            Text(
                                                "감상 후기를 남겨주세요!",
                                                style = MaterialTheme.typography.bodyMedium,
                                                color = Gray700,
                                                modifier = Modifier.padding(top = 12.dp)
                                            )
                                            var userRating by remember { mutableStateOf(0) }
                                            ReviewStarInput(
                                                rating = userRating,
                                                onRatingChange = { newRating ->
                                                    userRating = newRating
                                                    rootNavigator?.push(ReviewScreen(1, "콘텐츠",contentData.titleKr,newRating))
                                                }
                                            )
                                            Spacer(Modifier.height(12.dp))
                                        }
                                        Spacer(Modifier.height(4.dp))
                                            Column(
                                                modifier = Modifier.background(White).fillMaxWidth(),
                                                verticalArrangement = Arrangement.spacedBy(8.dp)
                                            ) {
                                                contentReviews.take(2).forEach { review ->
                                                    ReviewList(
                                                        name = review.username,
                                                        score = review.rating,
                                                        date = review.createdAt,
                                                        text = review.comment
                                                    )
                                                }
                                            }
                                    }
                                }

                                Spacer(Modifier.height(20.dp))
                                Text(
                                    "촬영지",
                                    modifier = Modifier.padding(horizontal = 20.dp),
                                    style = MaterialTheme.typography.titleMedium
                                )
                                Spacer(Modifier.height(4.dp))
                                LazyRow(
                                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                                    contentPadding = PaddingValues(horizontal = 20.dp)
                                ) {
                                    items(5) { index ->
                                        LocationItemSmall(
                                            /*
                                                bookmark = item.bookmark,
                                                image = item.image,
                                                title = item.title,
                                                rank = item.rank,*/
                                            onClick = {
                                                println("ㅇㅇ 클릭됨")
                                            }
                                        )
                                    }
                                }
                                Spacer(Modifier.height(20.dp))
                                Text(
                                    "출연진",
                                    modifier = Modifier.padding(horizontal = 20.dp),
                                    style = MaterialTheme.typography.titleMedium
                                )
                                Spacer(Modifier.height(4.dp))
                                LazyRow(
                                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                                    contentPadding = PaddingValues(horizontal = 20.dp)
                                ) {
                                    items(5) { index ->
                                        Cast("김승혁", "감독")
                                    }
                                }
                                Spacer(Modifier.height(20.dp))
                                Text(
                                    "영상",
                                    modifier = Modifier.padding(horizontal = 20.dp),
                                    style = MaterialTheme.typography.titleMedium
                                )
                                Spacer(Modifier.height(4.dp))

                                LazyRow(
                                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                                    contentPadding = PaddingValues(horizontal = 20.dp)
                                ) {
                                    when {
                                        youtubeUiState.isYoutubeLoading -> {
                                            item {
                                                Box(
                                                    modifier = Modifier.width(284.dp)
                                                        .height(160.dp),
                                                    contentAlignment = Alignment.Center
                                                ) {
                                                    CircularProgressIndicator()
                                                }
                                            }
                                        }

                                        youtubeUiState.videos.isNotEmpty() -> {
                                            items(
                                                items = youtubeUiState.videos,
                                                key = { video -> video.videoId }
                                            ) { video ->
                                                val uriHandler = LocalUriHandler.current

                                                YoutubeVideoItem(
                                                    video = video,
                                                    onClick = {
                                                        val youtubeUrl =
                                                            "https://www.youtube.com/watch?v=${video.videoId}"
                                                        uriHandler.openUri(youtubeUrl)
                                                    }
                                                )
                                            }
                                        }

                                        else -> {
                                            item {
                                                Box(
                                                    modifier = Modifier.width(284.dp)
                                                        .height(160.dp)
                                                        .padding(16.dp),
                                                    contentAlignment = Alignment.Center
                                                ) {
                                                    Text(
                                                        text = youtubeUiState.youtubeErrorMessage
                                                            ?: "관련 영상이 없습니다.",
                                                        color = Gray700,
                                                        fontSize = 14.sp
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }
                                Spacer(Modifier.height(20.dp))
                            }
                        }
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(currentImageHeight) // 스크롤에 따라 높이가 변함
                            .graphicsLayer {
                                translationY = if (currentImageHeightPx > minImageHeightPx) {
                                    0f
                                } else {
                                    minImageHeightPx - currentImageHeightPx
                                }
                            }
                    ) {
                        when {
                            isLoading -> {
                                Box(modifier = Modifier.fillMaxSize().background(Color.LightGray))
                            }
                            imageBitmap != null -> {
                                Image(
                                    bitmap = imageBitmap!!,
                                    contentDescription = "배경 이미지",
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
                                .fillMaxSize()
                                .background(
                                    brush = Brush.verticalGradient(
                                        colors = listOf(
                                            Color.Transparent, BlackTransparent,
                                            Color.Black
                                        ),
                                        startY = currentImageHeightPx / 2
                                    )
                                )
                        )

                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 20.dp)
                        ) {
                            val rootNavigator = LocalRootNavigator.current
                            Row {
                                Icon(
                                    painter = painterResource(Res.drawable.ic_back),
                                    contentDescription = "뒤로가기",
                                    modifier = Modifier.width(24.dp).height(64.dp)
                                        .padding(top = 40.dp).clickable {
                                        rootNavigator?.pop()
                                    }
                                )
                                Spacer(Modifier.weight(1f))
                                Image(
                                    painter = bookmark,
                                    contentDescription = "북마크",
                                    modifier = Modifier
                                        .height(90.dp)
                                        .width(50.dp)
                                        .clickable {
                                            internalIsLiked = !internalIsLiked
                                            if (contentData.isLiked) {
                                                screenModel.contentLikeCancel(contentData.id)
                                            } else {
                                                screenModel.contentLike(contentData.id)
                                            }
                                        }
                                )
                            }

                            Spacer(modifier = Modifier.weight(1f))

                            Column(modifier = Modifier.padding(bottom = 20.dp)) {
                                Row(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
                                    contentData.platforms.forEach { platform ->
                                        when (platform) {
                                            "netflix" -> Image(
                                                painter = painterResource(Res.drawable.img_netflix),
                                                contentDescription = "Netflix",
                                                modifier = Modifier.size(24.dp)
                                            )

                                            "watcha" -> Image(
                                                painter = painterResource(Res.drawable.img_watcha),
                                                contentDescription = "Watcha",
                                                modifier = Modifier.size(24.dp)
                                            )

                                            "disney" -> Image(
                                                painter = painterResource(Res.drawable.img_disney),
                                                contentDescription = "disney",
                                                modifier = Modifier.size(24.dp)
                                            )

                                            "coupang" -> Image(
                                                painter = painterResource(Res.drawable.img_coupang),
                                                contentDescription = "coupang",
                                                modifier = Modifier.size(24.dp)
                                            )

                                            "wave" -> Image(
                                                painter = painterResource(Res.drawable.img_wave),
                                                contentDescription = "wave",
                                                modifier = Modifier.size(24.dp)
                                            )

                                            "tving" -> Image(
                                                painter = painterResource(Res.drawable.img_tving),
                                                contentDescription = "tving",
                                                modifier = Modifier.size(24.dp)
                                            )

                                            else -> {}
                                        }
                                    }
                                }
                                Spacer(Modifier.height(4.dp))
                                Text(
                                    contentData.titleKr,
                                    color = Color.White,
                                    style = MaterialTheme.typography.headlineLarge
                                )
                                Spacer(Modifier.height(4.dp))
                                Text(
                                    contentData.titleEng,
                                    color = Color.White,
                                    style = MaterialTheme.typography.labelSmall
                                )
                                Spacer(Modifier.height(4.dp))
                                Row {
                                    Text(
                                        contentData.year.toString(),
                                        color = Color.White,
                                        style = MaterialTheme.typography.labelLarge
                                    )
                                    Spacer(Modifier.width(8.dp))
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        contentData.contentCategories.forEachIndexed { index, category ->
                                            Text(
                                                text = category.toString(),
                                                color = Color.White,
                                                style = MaterialTheme.typography.labelSmall
                                            )
                                            if (index < contentData.contentCategories.lastIndex) {
                                                Text(
                                                    text = "・",
                                                    color = Color.White,
                                                    style = MaterialTheme.typography.bodyMedium
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
