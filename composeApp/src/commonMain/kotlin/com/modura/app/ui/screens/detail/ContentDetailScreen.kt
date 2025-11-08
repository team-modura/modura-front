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
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import com.modura.app.LocalRootNavigator
import com.modura.app.ui.components.*
import com.modura.app.ui.screens.camera.SceneCameraScreen
import com.modura.app.ui.theme.*
import modura.composeapp.generated.resources.*
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview


data class ContentDetailScreen(val title: String) : Screen {
    override val key: String = "ContentDetailScreen_$title"

    @Composable
    override fun Content() {
        val screenModel: DetailScreenModel = getScreenModel()
        val uiState by screenModel.uiState
        val rootNavigator = LocalRootNavigator.current

        LaunchedEffect(Unit) {
            screenModel.getYoutubeVideos("기묘한 이야기 공식 예고편")
        }
        val ott: List<String> = listOf("netflix", "watcha")
        val categories = listOf("공포", "SF", "스릴러", "미국", "다크 판타지", "미스터리", "시대극")
        val story = "인디애나주의 작은 마을에서 행방불명된 소년. 이와 함께 미스터리한 힘을 가진 소녀가 나타나고, 마을에는 기묘한 현상들이 일어나기 시작한다. 아들을 찾으려는 엄마와 마을 사람들은 이제 정부의 일급비밀 실험의 실체와 무시무시한 기묘한 현상들에 맞서야 한다"

        BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
            val initialImageHeight = this.maxHeight
            val minImageHeight = 240.dp
            val bookmark = true
            val bookmark2=if(bookmark) painterResource(Res.drawable.img_bookmark_big_selected) else painterResource(Res.drawable.img_bookmark_big_unselected)

            val lazyListState = rememberLazyListState()
            val density = LocalDensity.current

            val initialImageHeightPx = with(density) { initialImageHeight.toPx() }
            val minImageHeightPx = with(density) { minImageHeight.toPx() }

            // --- 2. 스크롤 값 계산 (로직은 동일) ---
            val scrollOffset by remember {
                derivedStateOf {
                    if (lazyListState.firstVisibleItemIndex == 0) {
                        lazyListState.firstVisibleItemScrollOffset.toFloat()
                    } else {
                        initialImageHeightPx - minImageHeightPx
                    }
                }
            }

            val currentImageHeightPx = (initialImageHeightPx - scrollOffset).coerceAtLeast(minImageHeightPx)
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
                                Spacer(Modifier.height(8.dp))
                                Text(
                                    "줄거리",
                                    style = MaterialTheme.typography.titleLarge,
                                    modifier = Modifier.padding(horizontal = 20.dp)
                                )
                                Spacer(Modifier.height(8.dp))
                                Text(
                                    story,
                                    style = MaterialTheme.typography.bodySmall,
                                    modifier = Modifier.padding(horizontal = 20.dp)
                                )
                                Spacer(Modifier.height(12.dp))
                                Column(
                                    verticalArrangement = Arrangement.spacedBy(4.dp),
                                    modifier = Modifier.padding(horizontal = 20.dp)
                                ) {
                                    ott.forEach { platform ->
                                        OttPlayButton(
                                            ott = platform,
                                            onClick = {
                                                println("$platform PLAY 버튼 클릭됨!")
                                            }
                                        )
                                    }
                                }
                                Column(Modifier.padding(vertical = 12.dp)) {
                                    Row(modifier = Modifier.padding(horizontal = 20.dp)) {
                                        Text("리뷰", style = MaterialTheme.typography.titleLarge)
                                        Spacer(Modifier.weight(1f))
                                        Text("전체보기", style = MaterialTheme.typography.bodySmall)
                                    }
                                    Spacer(Modifier.height(4.dp))
                                    Box(
                                        modifier = Modifier.background(White)
                                            .clip(RoundedCornerShape(8.dp))
                                            .padding(horizontal = 20.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Review(4.5f, listOf(10, 4, 6, 2, 3))
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
                                                rootNavigator?.push(ReviewScreen(1))
                                            }
                                        )
                                        Spacer(Modifier.height(12.dp))
                                    }
                                    Spacer(Modifier.height(4.dp))
                                    Column(
                                        modifier = Modifier.background(White),
                                        verticalArrangement = Arrangement.spacedBy(8.dp)
                                    ) {
                                        ReviewList(
                                            "김승혁",
                                            4.5f,
                                            "2023.07.15",
                                            "아이들에 대한 묘사가 너무 절묘하다. 유난히 똑똑한 아이들임을 보여주면서도 순진함 속에 그들은 그들만의 규칙이 있다. 어리다고 미성숙하게만 그리는 게 아니라 하나의 인격체로서 다룸. 일레븐한테 가발 씌우는 의미 불명의 행태를 빼면 굉장히 재밌게 봤다. 시즌 2가 나와주길 기대."
                                        )
                                        ReviewList(
                                            "김승혁",
                                            3.5f,
                                            "2004.06.11",
                                            "스토리, 음악, 캐릭터까지 '슈퍼 에이트'보다 더 7080스럽고 사랑스럽지만 동시에 중독성 강한 SF호러. 아동, 하이틴, 미스터리를 모두 아름답게 조화시키며 깜찍함과 공포를 둘 다 느낄 수 있는 러브레터 이상의 수작."
                                        )
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
                                    ){
                                        items(5){index ->
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
                                    uiState.isYoutubeLoading -> {
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
                                    uiState.videos.isNotEmpty() -> {
                                        items(
                                            items = uiState.videos,
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
                                                    text = uiState.youtubeErrorMessage
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
                    Image(
                        painter = painterResource(Res.drawable.img_example),
                        contentDescription = "배경 이미지",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                brush = Brush.verticalGradient(
                                    colors = listOf(Color.Transparent, BlackTransparent,
                                        Color.Black),
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
                        Row{
                            Icon(
                                painter = painterResource(Res.drawable.ic_back),
                                contentDescription = "뒤로가기",
                                modifier = Modifier.width(24.dp).height(64.dp).padding(top = 40.dp).clickable {
                                    rootNavigator?.pop()
                                }
                            )
                            Spacer(Modifier.weight(1f))
                            Image(
                                painter = bookmark2,
                                contentDescription = "북마크",
                                modifier = Modifier
                                    .height(90.dp)
                                    .width(50.dp)
                                    .clickable {
                                        println("북마크 클릭됨")
                                    }
                            )
                        }

                        Spacer(modifier = Modifier.weight(1f))

                        Column(modifier = Modifier.padding(bottom = 20.dp)) {
                            Row(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
                                ott.forEach { platform ->
                                    when(platform) {
                                        "netflix" -> Image(painter = painterResource(Res.drawable.img_netflix), contentDescription = "Netflix", modifier = Modifier.size(24.dp))
                                        "watcha" -> Image(painter = painterResource(Res.drawable.img_watcha), contentDescription = "Watcha", modifier = Modifier.size(24.dp))
                                        "disney" -> Image(painter = painterResource(Res.drawable.img_disney), contentDescription = "disney", modifier = Modifier.size(24.dp))
                                        "coupang" -> Image(painter = painterResource(Res.drawable.img_coupang), contentDescription = "coupang", modifier = Modifier.size(24.dp))
                                        "wave" -> Image(painter = painterResource(Res.drawable.img_wave),contentDescription = "wave", modifier = Modifier.size(24.dp))
                                        "tving" -> Image(painter = painterResource(Res.drawable.img_tving), contentDescription = "tving", modifier = Modifier.size(24.dp))
                                        else -> {}
                                    }
                                }
                            }
                            Spacer(Modifier.height(4.dp))
                            Text("타이틀", color = Color.White, style = MaterialTheme.typography.headlineLarge)
                            Spacer(Modifier.height(4.dp))
                            Text("타이틀 (영어로)", color = Color.White, style = MaterialTheme.typography.labelSmall)
                            Spacer(Modifier.height(4.dp))
                            Row{
                                Text("2016", color = Color.White, style = MaterialTheme.typography.labelLarge)
                                Spacer(Modifier.width(8.dp))
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    categories.forEachIndexed { index, category ->
                                        Text(
                                            text = category,
                                            color = Color.White,
                                            style = MaterialTheme.typography.labelSmall
                                        )
                                        if (index < categories.lastIndex) {
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
