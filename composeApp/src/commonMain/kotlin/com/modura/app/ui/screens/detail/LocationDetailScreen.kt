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
import androidx.compose.ui.draw.shadow
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
import com.modura.app.data.dev.DummyProvider
import com.modura.app.data.dev.PlaceInfo
import com.modura.app.ui.components.*
import com.modura.app.ui.screens.camera.SceneCameraScreen
import com.modura.app.ui.theme.*
import modura.composeapp.generated.resources.*
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview


data class LocationDetailScreen(val id: Int?=1) : Screen {
    override val key: String = "ContentDetailScreen_$id"

    @Composable
    override fun Content() {

        val place = remember(id) { DummyProvider.dummyPlaces.find { it.id == id } }

        val rootNavigator = LocalRootNavigator.current
        val screenModel: DetailScreenModel = getScreenModel()
        val uiState by screenModel.uiState

        LaunchedEffect(Unit) {
            screenModel.getYoutubeVideos("기묘한 이야기 공식 예고편")
        }
        val ott: List<String> = listOf("netflix", "watcha")
        val categories = listOf("공포", "SF", "스릴러", "미국", "다크 판타지", "미스터리", "시대극")
        val story =
            "인디애나주의 작은 마을에서 행방불명된 소년. 이와 함께 미스터리한 힘을 가진 소녀가 나타나고, 마을에는 기묘한 현상들이 일어나기 시작한다. 아들을 찾으려는 엄마와 마을 사람들은 이제 정부의 일급비밀 실험의 실체와 무시무시한 기묘한 현상들에 맞서야 한다"

        LazyColumn(
            modifier = Modifier.fillMaxSize().background(Gray100)
        ) {
            if (place != null) { //추후 삭제
                item {
                    Column(modifier = Modifier.fillMaxWidth().padding(top = 60.dp, bottom = 20.dp, start = 20.dp, end = 20.dp)) {
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
                                    // 스틸컷 네비 추가
                                    rootNavigator?.push(SceneCameraScreen(sceneImageRes = "img_example"))
                                    println("스틸컷 찍으러가보자")
                                }
                            )
                        }
                        Spacer(Modifier.height(20.dp))
                        Text(place.name, style = MaterialTheme.typography.headlineLarge)
                        Spacer(Modifier.height(4.dp))
                        Row {
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
                        Spacer(Modifier.height(4.dp))
                        Text(place.address, style = MaterialTheme.typography.labelSmall)
                        Spacer(Modifier.height(16.dp))
                        Image(
                            painter = painterResource(Res.drawable.img_example),
                            contentDescription = null,
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
                    }
                }
                item {
                    Column {
                        Spacer(Modifier.height(20.dp))
                        Text(
                            modifier = Modifier
                                .padding(horizontal = 20.dp),
                            text = "${place.name}에서 촬영한 콘텐츠",
                            style = MaterialTheme.typography.titleMedium,
                        )
                        Spacer(Modifier.height(4.dp))
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            contentPadding = PaddingValues(horizontal = 20.dp)
                        ) {
                            items(5) { index ->
                                LocationItemSmall(
                                    /*bookmark = item.bookmark,
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
                                //리뷰 작성 칸 활성화
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
                        modifier = Modifier.background(White),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        ReviewLocationList(
                            "김승혁",
                            4.5f,
                            "2023.07.15",
                            "아이들에 대한 묘사가 너무 절묘하다. 유난히 똑똑한 아이들임을 보여주면서도 순진함 속에 그들은 그들만의 규칙이 있다. 어리다고 미성숙하게만 그리는 게 아니라 하나의 인격체로서 다룸. 일레븐한테 가발 씌우는 의미 불명의 행태를 빼면 굉장히 재밌게 봤다. 시즌 2가 나와주길 기대."
                        )
                        ReviewLocationList(
                            "김승혁",
                            3.5f,
                            "2004.06.11",
                            "스토리, 음악, 캐릭터까지 '슈퍼 에이트'보다 더 7080스럽고 사랑스럽지만 동시에 중독성 강한 SF호러. 아동, 하이틴, 미스터리를 모두 아름답게 조화시키며 깜찍함과 공포를 둘 다 느낄 수 있는 러브레터 이상의 수작."
                        )
                    }
                }
            }
        }
    }
}
