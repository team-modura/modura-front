package com.modura.app.ui.screens.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import com.modura.app.LocalRootNavigator
import com.modura.app.ui.components.OttPlayButton
import com.modura.app.ui.components.Review
import com.modura.app.ui.screens.home.HomeScreen
import com.modura.app.ui.theme.BlackTransparent
import com.modura.app.ui.theme.Gray100
import com.modura.app.ui.theme.Gray900
import com.modura.app.ui.theme.White
import modura.composeapp.generated.resources.*
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.time.ExperimentalTime

object ContentDetailScreen : Screen {
    override val key: String = "ContentDetailScreenKey"

    @Composable
    override fun Content() {
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
                            Column(
                                modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp)
                            ) {
                                Text(
                                    "줄거리",
                                    style = MaterialTheme.typography.titleLarge
                                )
                                Spacer(Modifier.height(8.dp))
                                Text(
                                    story,
                                    style = MaterialTheme.typography.bodySmall
                                )
                                Spacer(Modifier.height(12.dp))
                                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                    ott.forEach { platform ->
                                        OttPlayButton(
                                            ott = platform,
                                            onClick = {
                                                println("$platform PLAY 버튼 클릭됨!")
                                            }
                                        )
                                    }
                                }
                                Column(Modifier.padding(vertical = 12.dp)){
                                    Row{
                                        Text("리뷰", style = MaterialTheme.typography.titleLarge)
                                        Spacer(Modifier.weight(1f))
                                        Text("전체보기", style = MaterialTheme.typography.bodySmall)
                                    }
                                    Spacer(Modifier.height(4.dp))
                                    Box(modifier = Modifier.background(White).clip(RoundedCornerShape(8.dp)), contentAlignment = Alignment.Center){
                                        Review(4.5f, listOf(10,4,6,2,3))
                                    }
                                }
                            }
                            Spacer(Modifier.height(400.dp))
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

@Preview
@Composable
private fun Preview() {
    ContentDetailScreen.Content()
}